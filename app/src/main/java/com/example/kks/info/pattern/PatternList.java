package com.example.kks.info.pattern;

public class PatternList {

    //필요한 것
    //각 카테고리마다의 수

    int movie, drama, doc, exhibit, music, musical, book;

    public PatternList(int movie, int drama, int doc, int exhibit, int musical, int book, int music){
        this.movie = movie;
        this.drama = drama;
        this.doc = doc;
        this.exhibit = exhibit;
        this.musical = musical;
        this.book = book;
        this.music = music;
    }

    public int get(int index) {
        switch (index) {
            case 0:
                return movie;
            case 1:
                return drama;
            case 3:
                return doc;
            case 4:
                return exhibit;
            case 5:
                return musical;
            case 6:
                return book;
            case 7:
                return music;
            default:
                return 0;
        }
    }
}
