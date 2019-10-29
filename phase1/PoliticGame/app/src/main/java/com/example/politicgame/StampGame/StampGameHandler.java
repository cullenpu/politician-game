package com.example.politicgame.StampGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* TODO: fix the index out of bound bug
*  TODO: fix the over rated system
*   TODO: Optimize the scoring system
* */
public class StampGameHandler {
    private List<Word> verbs;
    private List<Word> nouns;
    private List<Proposal> prompts;
    private Proposal currentPrompt;

    private double getRandomDoubleBetweenRange (double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }

    private void addWordToList (List<String> stringList, List<Word> wordList, String word){
         int min = 1;
         int max = 5;

        switch (word) {
            case "posVerb":
                for (String item : stringList) {
                    int posCategory = (int)(getRandomDoubleBetweenRange(min, max));
                    wordList.add(new Verb(item, posCategory));
                }
                break;
            case "negVerb":
                for (String item : stringList) {
                    int negCategory = (int)(getRandomDoubleBetweenRange(min, max));
                    wordList.add(new Verb(item, -negCategory));
                }
                break;
            case "negNounYA":
                for (String item : stringList) {
                    int negCategory = (int)(getRandomDoubleBetweenRange(min, max));
                    wordList.add(new Noun(item, -negCategory, true));
                }
                break;
            case "negNounNA":
                for (String item : stringList) {
                    int negCategory = (int)(getRandomDoubleBetweenRange(min, max));
                    wordList.add(new Noun(item, -negCategory, false));
                }
                break;
            case "posNounYA":
                for (String item : stringList) {
                    int posCategory = (int)(getRandomDoubleBetweenRange(min, max));
                    wordList.add(new Noun(item, posCategory, true));
                }
                break;
            case "posNounNA":
                for (String item : stringList) {
                    int posCategory = (int)(getRandomDoubleBetweenRange(min, max));
                    wordList.add(new Noun(item, posCategory, false));
                }
                break;
        }
    }

    public StampGameHandler(){
        verbs = new ArrayList<Word>();
        nouns = new ArrayList<Word>();
        prompts = new ArrayList<Proposal>();

        //Positive verbs that has a positive effect on the object
        List<String> verbListPositive = new ArrayList<String>(Arrays.asList(
                "campaign with the best interests of",
                "send aid to",
                "donate money to charities that work with",
                "gift a bouquet of flowers to"));

        addWordToList(verbListPositive, verbs, "posVerb");


        //Positive verbs that has a negative effect on the object
        List<String> verbListNegative = new ArrayList<String>(Arrays.asList(
                "send a police squad to detain and imprison",
                "punch the daylights out of",
                "launch nukes against",
                "send an army against",
                "launch an investigation against",
                "personally find and laugh at"));


        addWordToList(verbListNegative, verbs, "negVerb");


        //Positive nouns that are not amountable
        List<String> nounListPositiveNA = new ArrayList<String>(Arrays.asList(
                "Gandhi, a recently popular peace advocate who campaigns in India",
                "the popular late-night TV show host John Olive Oil",
                "Bill Rye, a once popular figure in science who recently published a paper on the benefits of foot rubs",
                "Toe-Knee, a rising star in the genre of Jazz, who recently released his hit album \"CS Blues\"",
                "Jacki, a citizen who has been wrongfully imprisoned in the country of Canada for not holding the door for the boy behind him"));

        addWordToList(nounListPositiveNA, nouns, "posNounNA");


        //Positive nouns that are amountable
        List<String> nounListPositiveYA = new ArrayList<String>(Arrays.asList(
                "puppies, specifically the ones at the Downtown Toronto Dog Shelter",
                "Boundless Peacocks, the very last of their species",
                "sad computer science students at the University of Toronto"));

        addWordToList(nounListPositiveYA, nouns, "posNounYA");


        //Negative nouns that are not amountable
        List<String> nounListNegativeNA = new ArrayList<String>(Arrays.asList(
                "the premier of Ontario, Canada",
                "the leader of North Korea",
                "Colin, a medical practitioner found to have cheated on his medical exams after a related illegal nose smuggling ring was busted",
                "Kavin, a phantom thief who masterminded the theft all the laptop chargers, but not the laptops, at the University of Toronto last Fall"));

        addWordToList(nounListNegativeNA, nouns, "negNounNA");


        //Negative nouns that are amountable
        List<String> nounListNegativeYA = new ArrayList<String>(Arrays.asList(
                "seal clubbers"));

        addWordToList(nounListNegativeYA, nouns, "negNounYA");
    }

    //Must be called first
    public void createPrompt(){
        //For when we have a male or female player
        String pronoun = "sir";
        String emptyListMessage = "Sorry, we do not have a new proposal for you yet, ";
        Verb emptyAction = new Verb("come back later", 0);
        Noun emptyNoun = new Noun(" sir", 0, false);

        int verbIndex = (int)(Math.random() * (verbs.size()));
        int nounIndex = (int)(Math.random() * (nouns.size()));

        if (verbs.isEmpty() || nouns.isEmpty()){
            currentPrompt = new Proposal(emptyListMessage, emptyAction, emptyNoun);

        } else {
            List<String> promptBeginList = new ArrayList<String>(Arrays.asList(
                    "Good afternoon " + pronoun + ", our campaign researchers speculate that it may be in our best interests to declare that you would",
                    "This just in " + pronoun + ", there are rumors that some of our more excited supporters have been advocating that you would",
                    "I'm here for a briefing " + pronoun + ", there seems to be a growing support for campaign leaders who would",
                    "Greetings " + pronoun + ", here to drop off a report about our supporters in the west. It seems that there are rumors that you would",
                    "Nice to meet you " + pronoun + ". I am one of your many campaign assistants and was wondering if you would advocate to"));

            String promptBegin = promptBeginList.get((int) (Math.random() * (promptBeginList.size())));

            if (((Noun) nouns.get(nounIndex)).getAmountable()) {
                int amount = (int) (Math.random() * (1000));
                currentPrompt = new Proposal(promptBegin, ((Verb) verbs.remove(verbIndex)), ((Noun) nouns.remove(nounIndex)), amount);
            } else {
                currentPrompt = new Proposal(promptBegin, ((Verb) verbs.remove(verbIndex)), ((Noun) nouns.remove(nounIndex)));
            }

            prompts.add(currentPrompt);
        }
    }

    public Proposal getCurrentPrompt(){return currentPrompt;}

    public int getCurrentPromptScore(){return currentPrompt.getCategory();}

    public int getPromptsDone(){return prompts.size();}
}