package com.example.taskand;

import android.os.Bundle;

import java.util.List;

public class Test {
    protected void main() {
        Task A = new Task("A");
        Task B = new Task("B");
        Task C = new Task("C");
        Task D = new Task("D");
        Task T = new Task("T");
        T.addChild(A);
        T.addChild(B);
        A.addChild(C);
        A.addChild(D);

        // afficher l'arbre
        // afficher T
        Boolean end = Boolean.FALSE;
        Task current = T;

        //afficher T
        List<Task> soustaches_t = current.getChilds();
        int max = soustaches_t.size();
        //int i = 0;
        System.out.println("Sous taches de T");
        for (int i=0; i < max; i++){
            // afficher
            Task fils = soustaches_t.get(i);
            System.out.println(fils.getName());
        }
    }
}
