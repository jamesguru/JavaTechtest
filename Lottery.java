import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Lottery {
    private ArrayList<Integer>[] combinations;
    private ArrayList<Integer> prizes;
    private ArrayList<String> winners;
    private Result best;

    class Result{
        ArrayList<Integer> combination;
        double score;

        public Result(ArrayList<Integer> combination, double score) {
            this.combination = combination;
            this.score = score;
        }
    }

    public Lottery() {
        prizes = new ArrayList<>();
        winners = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter list of prizes: ");
        String prizes = sc.nextLine();
        System.out.print("Enter list of winners: ");
        String winners = sc.nextLine();

        for(String str: prizes.split(",")){
            this.prizes.add(Integer.parseInt(str));
        }
        for(String str: winners.split(",")){
            this.winners.add(str);
        }
        combinations = new ArrayList[100];
        generateSolutions();
        getBestSolution();
    }

    public void generateSolutions(){
        Random random = new Random();
        for(int i = 0; i < combinations.length; i++){
            combinations[i] = new ArrayList<>();
            for(int j = 0; j < prizes.size(); j++){
                combinations[i].add(random.nextInt(winners.size()));
            }
        }
    }

    public double calculateFitness(ArrayList<Integer> combination){
        int[] winnings = new int[winners.size()];

        for(int i = 0; i < combination.size(); i++){
            winnings[combination.get(i)]+=prizes.get(i);
        }

        int diff = 0;
        for(int i = winnings.length-1; i > 0; i--){
            diff+=(Math.abs(winnings[i] - winnings[i-1]) * 10);
        }

        if (diff == 0)
            return 99999;
        else return 1/diff;
    }

    public void printBestesult(){
//        for(int i = 0; i < combinations.length; i++){
//            for(int j = 0; j < combinations[i].size(); j++){
//                System.out.print(combinations[i].get(j)+" ");
//            }
//            System.out.println();
//        }
        String[] winnings = new String[winners.size()];

        for(int i = 0; i < best.combination.size(); i++){
            if(winnings[best.combination.get(i)] != null) {
                winnings[best.combination.get(i)]+=","+prizes.get(i);
            }else
                winnings[best.combination.get(i)] = ""+prizes.get(i);
        }
        for(int i = 0; i < winners.size(); i++){
            System.out.println(winners.get(i)+": "+winnings[i]);
        }
//        System.out.println(best.score);
    }

    public void getBestSolution(){
        ArrayList<Result> results = new ArrayList<>();
        int generations = 0;
        while(best == null || best.score != 99999){
            for(ArrayList<Integer> combination: combinations){
                Result r = new Result(combination, calculateFitness(combination));
                results.add(r);
            }

            getNextGeneration(results);
            if(generations > 100)
                break;
            generations++;
//            System.out.println(generations);
        }
    }

    public void getNextGeneration(ArrayList<Result> results){
        for(int i = 0; i < results.size(); i++){
            for(int j = 0; j < results.size()-1; j++){
                Result r1 = results.get(j);
                Result r2 = results.get(j+1);
                if(r2.score > r1.score){
                    results.set(j, r2);
                    results.set(j+1, r1);
                }
            }
        }

        if(best == null || best.score < results.get(0).score)
            best = results.get(0);
        int arrayCount = 0;
        for(int i = 0; i < results.size(); i++){
            ArrayList<Integer> comb1 = results.get(i).combination;
            ArrayList<Integer> comb2 = results.get(i+1).combination;
            ArrayList<Integer> child1 = new ArrayList<>();
            ArrayList<Integer> child2 = new ArrayList<>();
            Random random = new Random();
            for(int j = 0; j < comb1.size(); j++){
                if(j > comb1.size()/2){
                    child1.add(comb1.get(j));
                    child2.add(comb2.get(j));
                }else{
                    child1.add(comb2.get(j));
                    child2.add(comb1.get(j));
                }
                int r = random.nextInt(10);
                if(r <= 2){
                    child1.set(child1.size()-1, random.nextInt(winners.size()));
                }else if (r <= 4){
                    child2.set(child2.size()-1, random.nextInt(winners.size()));
                }

            }



            if(arrayCount < combinations.length){
                combinations[arrayCount++] = child1;
            }else{
                break;
            }

            if(arrayCount < combinations.length){
                combinations[arrayCount++] = child2;
            }else{
                break;
            }
        }

    }

    public static void main(String[] args) {
        Lottery lottery = new Lottery();
        lottery.printBestesult();
    }
}
