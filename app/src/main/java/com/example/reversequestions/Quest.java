package com.example.reversequestions;

import java.util.ArrayList;

public class Quest {

        public String quest;
        public String ans1;
        public String ans2;
        public int goodAns;

        public Quest(String quest, String ans1, String ans2, int goodAns) {
            this.quest = quest;
            this.ans1 = ans1;
            this.ans2 = ans2;
            this.goodAns = goodAns;
        }

}
