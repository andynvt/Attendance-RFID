package other.custom;

import java.io.*;
import java.util.*;
import javax.comm.*;

public class SimpleRead implements Runnable, SerialPortEventListener {

    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;

    public static void main(String[] args) throws Exception {
        portList = CommPortIdentifier.getPortIdentifiers();
        System.out.println("SimpleRead Started.");
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println("Found " + portId.getName());
                if (portId.getName().equals("COM5")) {
                    OutputStream outputStream;
                    SerialPort writePort = (SerialPort) portId.open("SimpleWriteApp", 2000);
                    writePort.setSerialPortParams(2400,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    outputStream = writePort.getOutputStream();
                    outputStream.write("AT+CENG=2".getBytes());
                    writePort.close();
                    SimpleRead reader = new SimpleRead();
                }
            }
        }
    }

    public SimpleRead() {
        try {
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        } catch (PortInUseException e) {
        }
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
        }
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(2400, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
        }
        readThread = new Thread(this);
        readThread.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[20];

                try {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                    }
                    System.out.print(new String(readBuffer));
                } catch (IOException e) {
                }
                break;
        }
    }
}
