import java.io.*;
import java.util.*;

class Soldier {
    String name;
    long score;
    int pos;
}

public class Spartan {

    public static void insertionSort(List<Soldier> soldierHeap) {
        for (int i = 0; i < soldierHeap.size(); i++) {
            Soldier current = soldierHeap.get(i);
            int j = i - 1;
            while (j >= 0 && (current.score < soldierHeap.get(j).score)) {
                soldierHeap.set(j + 1, soldierHeap.get(j));
                soldierHeap.get(j + 1).pos = j + 1;
                j--;
            }
            soldierHeap.set(j + 1, current);
            soldierHeap.get(j + 1).pos = j + 1;

        }
    }

    public static void createHash(List<Soldier> soldierHeap, HashMap<String, Soldier> soldierHash) {
        for (int i = 0; i < soldierHeap.size(); i++) {
            soldierHash.put(soldierHeap.get(i).name, soldierHeap.get(i));
        }
    }

    public static void main(String[] args) {

        List<Soldier> minHeap = new ArrayList<Soldier>();
        HashMap<String, Soldier> soldierHash = new HashMap<String, Soldier>();

        Scanner reader = new Scanner(System.in);

        int n = reader.nextInt();
        reader.nextLine();
        if (n <= (10 * 10 * 10 * 10 * 10)) {

            for (int i = 0; i < n; i++) {
                String data = reader.nextLine();
                String[] dataArray = data.split(" ", 2);
                String soldierName = dataArray[0].trim();
                long soldierScore = Long.parseLong(dataArray[1]);
                if (soldierScore <= (10 * 10 * 10 * 10 * 10 * 10 * 10 * 10 * 10)) {
                    Soldier newSoldier = new Soldier();
                    newSoldier.score = soldierScore;
                    newSoldier.name = soldierName;
                    minHeap.add(newSoldier);
                    soldierHash.put(soldierName, newSoldier);
                }

            }

            String outputs = "";

            int m = reader.nextInt();
            reader.nextLine();

            if (m <= (10 * 10 * 10 * 10 * 10)) {

                for (int i = 0; i < m; i++) {
                    String data = reader.nextLine();
                    String[] dataArray = data.split(" ", 2);
                    switch (dataArray[0]) {
                        case "1":
                            String[] dataArray1 = dataArray[1].split(" ", 2);
                            String name = dataArray1[0];
                            long scoreIncrease = Long.parseLong(dataArray1[1]);
                            // if((scoreIncrease) <= (10*10*10*10*10*10*10*10*10)){
                            soldierHash.get(name).score += scoreIncrease;
                            // }
                            // insertionSort(minHeap);

                            break;

                        case "2":
                            insertionSort(minHeap);
                            long k = Long.parseLong(dataArray[1]);
                            while (minHeap.get(0).score < k) {
                                minHeap.remove(0);
                            }
                            outputs += minHeap.size() + "\n";

                            break;
                    }
                }

            }
            System.out.println(outputs);

        }

    }

}