package DigitalPaymentServices;

interface readWrite{
    //Required methods for reading and writing files
    void write(String x, String path);
    void read(String path) throws Exception;
}
