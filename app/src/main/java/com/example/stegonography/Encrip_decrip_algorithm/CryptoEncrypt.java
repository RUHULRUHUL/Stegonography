package com.example.stegonography.Encrip_decrip_algorithm;

public class CryptoEncrypt {

    public String getCryptoEncrypt(String text, String key) {

        //edited by ruhul
        EncryptionAlgorithm ob = new EncryptionAlgorithm();
        ob.Text = text;
        ob.Key = key;
        return ob.encrypt(ob);
    }


    public String getCryptoDecrypt(String text, String key){

        EncryptionAlgorithm ob = new EncryptionAlgorithm();
        ob.encoded = text;
        ob.Key = key;
        return ob.decrypt(ob);
    }

}
