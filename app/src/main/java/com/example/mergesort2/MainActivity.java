package com.example.mergesort2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listOfNumbersLV;
    private ArrayList<String> theListOfNumbersAsStrings;
    private ArrayAdapter<String> theListOfNumbersAdapter;

    private ListView theCallsToMergeSortLV;
    private ArrayList<String> theListOfMergeSortCalls;
    private ArrayAdapter<String> theCallsToMergeSortAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listOfNumbersLV = this.findViewById(R.id.listOfNumbersLV);

        //this guy will show the parts of the arraylist we are working on in mergesort
        this.theListOfMergeSortCalls = new ArrayList<String>();
        this.theCallsToMergeSortAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                this.theListOfMergeSortCalls);
        this.theCallsToMergeSortLV = this.findViewById(R.id.theCallsToMergeSortLV);
        this.theCallsToMergeSortLV.setAdapter(this.theCallsToMergeSortAdapter);


        this.theListOfNumbersAsStrings = new ArrayList<String>();
        this.theListOfNumbersAsStrings.add("5");
        this.theListOfNumbersAsStrings.add("2");
        this.theListOfNumbersAsStrings.add("8");
        this.theListOfNumbersAsStrings.add("3");
        this.theListOfNumbersAsStrings.add("13");
        this.theListOfNumbersAsStrings.add("5");
        this.theListOfNumbersAsStrings.add("5");
        this.theListOfNumbersAsStrings.add("8");
        this.theListOfNumbersAsStrings.add("1");

        this.theListOfNumbersAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                this.theListOfNumbersAsStrings);

        this.listOfNumbersLV.setAdapter(theListOfNumbersAdapter);
    }

    private String buildStringFromPartOfList(ArrayList<String> theList, int begin, int end) {
        String s = "";
        for (int i = begin; i <= end; i++) {
            s = s + theList.get(i) + " ";
        }
        return s;
    }

    private void mergeSort(ArrayList<String> theList, int begin, int end)
    {

        String currentPartOfArray = this.buildStringFromPartOfList(theList, begin, end);
        this.theListOfMergeSortCalls.add(currentPartOfArray);
        this.theCallsToMergeSortAdapter.notifyDataSetChanged();

    }

    public class MergeSorter extends Thread
    {
        private int[] ar;
        private int begin;
        private int end;

        public MergeSorter(int[] ar, int begin, int end) {
            this.ar = ar;
            this.begin = begin;
            this.end = end;
        }

        public void run() {
            System.out.println("*** " + this.getId() + " is starting between " + begin + " and " + end);
            if (begin != end) {
                int begin1 = begin;
                int end1 = (begin + end) / 2;
                int begin2 = end1 + 1;
                int end2 = end;

                MergeSorter leftHalf = new MergeSorter(ar, begin1, end1);
                MergeSorter rightHalf = new MergeSorter(ar, begin2, end2);
                leftHalf.start();
                rightHalf.start();
                try {
                    leftHalf.join();
                    rightHalf.join();
                    int[] temp = new int[end - begin + 1];
                    int pos1 = begin1;
                    int pos2 = begin2;

                    for (int i = 0; i < temp.length; i++) {
                        if (pos1 <= end1 && pos2 <= end2) {
                            if (ar[pos1] < ar[pos2]) {
                                temp[i] = ar[pos1];
                                pos1++;
                            } else {
                                temp[i] = ar[pos2];
                                pos2++;
                            }
                        } else if (pos1 <= end1) {
                            temp[i] = ar[pos1];
                            pos1++;
                        } else {
                            temp[i] = ar[pos2];
                            pos2++;
                        }
                    }
                }

                public void onMergeSortButtonClicked (View v)
                {
                    this.mergeSort(this.theListOfNumbersAsStrings,
                            0, this.theListOfNumbersAsStrings.size() - 1);
                }
            }
        }
    }
}