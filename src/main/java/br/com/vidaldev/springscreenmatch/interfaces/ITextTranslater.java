package br.com.vidaldev.springscreenmatch.interfaces;

import br.com.vidaldev.springscreenmatch.enums.TextLanguage;

public interface ITextTranslater {
    String translate(String text, TextLanguage language);
}
