package br.com.vidaldev.springscreenmatch.enums;

public enum TextLanguage {
    PT_BR("brazilian portuguese"),
    EN("english"),
    ES("spanish");

    public String label;

    TextLanguage(String label){
        this.label = label;
    }
}
