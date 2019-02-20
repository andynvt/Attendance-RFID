/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package text2speech;

import com.goebl.david.Response;
import com.goebl.david.Webb;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.json.JSONException;
import org.json.JSONObject;
import other.custom.StringUtils;

/**
 *
 * @author chuna
 */
public class Text2Speech {

    private static final String URL_FPT_AI = "http://api.openfpt.vn/text2speech/v4";
    private static final String API_KEY = "cc685f0e03cd480cbc131888a33c7772";
    private String content;

    private static Webb webb;
    private static String linkMp3;

    public Text2Speech() {
        setUp();
    }

    public Text2Speech(String content) {
        this.content = content.toLowerCase().trim();
        setUp();
    }

    private void setUp() {
        Webb.setGlobalHeader("api_key", API_KEY);
        webb = Webb.create();
        webb.setBaseUri(URL_FPT_AI);
    }

    private String getLink() {
        try {
            Response<JSONObject> result = webb.post(URL_FPT_AI).header("speed", -500)
                    .header("voice", "female").body(content).ensureSuccess()
                    .asJsonObject();
            JSONObject jsonResult = result.getBody();
            linkMp3 = jsonResult.getString("async");
        } catch (JSONException | com.goebl.david.WebbException e) {
            System.out.println("JSONExeption " + e.getMessage());
        }
        return linkMp3;
    }

    public void playSpeech() {
        String cont = StringUtils.unAccent(this.content.replaceAll("\\s+", "-"));
        File file = new File("src/main/resources/sound/" + cont + ".mp3");
        if (isExist(file)) {
            play(file);
        } else {
            playSpeechOnline();
        }
    }

    public void playSpeechOnline() {
        try {
            URLConnection conn = new URL(getLink()).openConnection();
            InputStream is = conn.getInputStream();
            String cont = StringUtils.unAccent(this.content.replaceAll("\\s+", "-"));
            try (OutputStream outstream = new FileOutputStream(new File("src/main/resources/sound/" + cont + ".mp3"))) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    outstream.write(buffer, 0, len);
                }
            }
            File file = new File("src/main/resources/sound/" + cont + ".mp3");
            play(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isExist(File soundFile) {
        try {
            FileInputStream fIS = new FileInputStream(soundFile);
        } catch (FileNotFoundException ex) {
            return false;
        }
        return true;
    }

    public void play(File soundFile) {
        try {
            FileInputStream fis = new FileInputStream(soundFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            try {
                final Player soundPlayer = new Player(bis);
                soundPlayer.play();
            } catch (JavaLayerException e) {
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        this.content = text.toLowerCase().trim();
    }

    public static void main(String[] args) {
        Text2Speech speech = new Text2Speech();
        speech.setContent("Bạn đã điểm danh rồi");
        speech.playSpeech();
    }
}
