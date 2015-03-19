package com.github.marcohk;

import com.github.marcohk.util.GenerationEntity;
import com.github.marcohk.util.GeneticAlgorithm;
import com.github.marcohk.util.GeneticAlgorithmBuilder;
import java.util.*;

public class Traveller {

    private static class City {
        final String name;
        final int x;
        final int y;

        public City(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return name +  "|" + x + "," + y + "|";
        }

        public String toCoordinateString() {
            return "|" + x + "," + y + "|";
        }
    }

    public static void main(String[] args) {
        Random rand = new Random(0);

        //Sample cities data
        List<City> cities = Arrays.asList(
                new City("A", 180, 200),
                new City("B", 80, 180),
                new City("C", 140, 180),
                new City("D", 20, 160),
                new City("E", 100, 160),
                new City("F", 200, 160),
                new City("G", 140, 140),
                new City("H", 40, 120),
                new City("I", 100, 120),
                new City("J", 180, 100),
                new City("K", 60, 80),
                new City("L", 120, 80),
                new City("M", 180, 60),
                new City("N", 20, 40),
                new City("O", 100, 40),
                new City("P", 200, 40),
                new City("Q", 20, 20),
                new City("R", 60, 20),
                new City("S", 160, 20),
                new City("T", 60, 200),
                new City("AA",55,123),
                new City("BB",92,165),
                new City("CC",108,53),
                new City("DD",62,107),
                new City("EE",178,89),
                new City("FF",23,69),
                new City("GG",32,7),
                new City("HH",139,181),
                new City("II",108,133),
                new City("JJ",174,35),
                new City("KK",5,64),
                new City("LL",168,107),
                new City("MM",174,51),
                new City("NN",115,68),
                new City("OO",193,108),
                new City("PP",165,56),
                new City("QQ",149,104),
                new City("RR",143,113),
                new City("SS",128,119),
                new City("TT",12,48),
                new City("UU",66,52),
                new City("VV",136,166),
                new City("WW",37,164),
                new City("XX",89,163),
                new City("YY",30,95),
                new City("ZZ",138,128),
                new City("[[",27,183),
                new City("\\",72,91),
                new City("]]",3,192),
                new City("^^",90,154)

                );

        final int INITIAL_POPULATION = 1000;
        final int GENERATION = 100;
        final int MAX_POPULATION = 1000;
        final float CROSSOVER_RATE = 0.5f;
        final float MUTATE_RATE = 0.3f;

        //Create a genetic algorithm using the GeneticAlgorithmBuilder with Java 8
        GeneticAlgorithm<ArrayList<City>, ArrayList<City>> algorithm =
            new GeneticAlgorithmBuilder<ArrayList<City>,ArrayList<City>>().with(() -> {
                        LinkedList<GenerationEntity<ArrayList<City>>> initialPopulation = new LinkedList<GenerationEntity<ArrayList<City>>>();

                        // Generate 1000 random paths
                        for (int i = 0; i < INITIAL_POPULATION; i++) {
                            ArrayList<City> path = new ArrayList<City>(cities);
                            Collections.shuffle(path, rand);
                            initialPopulation.add(new GenerationEntity<ArrayList<City>>(path, 0));
                        }

                        return initialPopulation;
                    }
            ).generation(GENERATION) // Generate 50 generations
            .geneticize((path) -> path) // Use the ArrayList<City> as the genetic directly
            .degeneticize((path) -> path) // Use the ArrayList<City> as the genetic directly
            .fitness((path) -> {
                int totalDistance = 0;


                int size = path.size();
                for (int i = 0; i < size; i++) {
                    City current = path.get(i);
                    City next = path.get((i + 1) % size); //Traveller travel back to the first city from last city

                    /*
                    //Calculate the manhattan distance for each route
                    totalDistance += (Math.abs(current.x - next.x) + Math.abs(current.y - current.y));
                    */

                    totalDistance += (Math.sqrt(Math.pow(current.x - next.x, 2) + Math.pow(current.y - next.y, 2)));
                }

                return 1.0f / totalDistance;
            }).selector((population, totalFitness, generation) -> {

                //Below is selection always selecting first n best elements. This might easily lead to local maximum
                //Another way is to use roulette wheel selection so that some non-best elements survive longer for crossover
                int size = population.size();

                Collections.sort(population, (e1,e2)-> new Float(e1.getFitness()).compareTo(e2.getFitness()));
                population.subList(0,Math.max(0, size-MAX_POPULATION)).clear();
                return population;
            }).crossover((g1, g2) -> {

                //Randomly picks a crossover point
                int size = g1.size();
                int pointcut = rand.nextInt(size);

                ArrayList<City> child = new ArrayList<City>(g1);

                for (int i = 0; i <= pointcut; i++) {
                    //Swap the city so that traveller can always travel all cities exactly once
                    City c1 = child.get(i);
                    City c2 = g2.get(i);
                    int j = child.indexOf(c2);
                    child.set(i, c2);
                    child.set(j, c1);
                }

                return child;
            }).crossoverRate(CROSSOVER_RATE) // Set crossover rate to 50%
                    .mutate((g) -> {

                        //Randomly swap a pair of element
                        int size = g.size();
                        int i = rand.nextInt(size);
                        int j = rand.nextInt(size);

                        ArrayList<City> mutate = new ArrayList<City>(g);
                        City temp = mutate.get(i);
                        mutate.set(i, mutate.get(j));
                        mutate.set(j, temp);

                        return mutate;

                    }).mutateRate(MUTATE_RATE)  // Set mutation rate to 50%
                    .seed(0) // Set seed for testing purpose
                    .debug(false) // Set debug mode to print each step
                    .build();

        LinkedList<GenerationEntity<ArrayList<City>>> result = algorithm.process();
        GenerationEntity<ArrayList<City>> best = Collections.max(result, (e1, e2) -> new Float(e1.getFitness()).compareTo(e2.getFitness()));

        System.out.println("Sub-optimal Result: " + best);
    }
}
