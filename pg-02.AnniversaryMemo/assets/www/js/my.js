function localize(locale, ja, en) {
  if("ja_JP" == locale) {
    return ja;
  } else {
    return en;
  }
}

function getCountry(locale) {
    if("ja_JP" == locale) {
        return "ja";
    }else if("en_US" == locale) {
        return "us";
    }else if("en_CA" == locale) {
        return "ca";
    }else if("en_GB" == locale) {
        return "uk";
    }else {
        return ".";
    }
}
