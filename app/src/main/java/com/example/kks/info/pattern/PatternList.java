package com.example.kks.info.pattern;

public class PatternList {

    //필요한 것
    //각 카테고리마다의 수

    //int movie, drama, doc, exhibit, music, musical, book;
    int show, etc, book, drama, play, movie, music, exhibit;

    public PatternList(int show, int book, int drama, int play, int movie, int music, int exhibit, int etc) {
        this.movie = movie;
        this.drama = drama;
        this.show = show;
        this.exhibit = exhibit;
        this.play = play;
        this.book = book;
        this.music = music;
        this.etc = etc;
    }

    public int get(int index) {
        switch (index) {
            case 0:
                return show;
            case 1:
                return book;
            case 2:
                return drama;
            case 3:
                return play;
            case 4:
                return movie;
            case 5:
                return music;
            case 6:
                return exhibit;
            case 7:
                return etc;
            default:
                return 0;
        }
    }
}