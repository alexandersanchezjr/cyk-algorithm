package model;

import java.util.*;

public class GIC {

    private char[] variables;
    private char[] symbols;
    private char initialSymbol;
    private HashMap<Character, String> productions;

    public GIC (String inputVariables, String inputSymbols, Character inputInitialSymbol, HashMap<Character, String> inputProductions) {
        variables = inputVariables.toCharArray();
        symbols = inputSymbols.toCharArray();
        initialSymbol = inputInitialSymbol;
        productions = inputProductions;
    }

    public char [] getVariables() {
        return variables;
    }

    public void setVariables(char [] variables) {
        this.variables = variables;
    }

    public char [] getSymbols() {
        return symbols;
    }

    public void setSymbols(char [] symbols) {
        this.symbols = symbols;
    }

    public char getInitialSymbol() {
        return initialSymbol;
    }

    public void setInitialSymbol(char initialSymbol) {
        this.initialSymbol = initialSymbol;
    }

    public HashMap<Character, String> getProductions() {
        return productions;
    }

    public void setProductions(HashMap<Character, String> productions) {
        this.productions = productions;
    }

    public boolean cykAlgorithm (String w) {
        return true;
    }

    public boolean isCNF () {
        return reachableVariables() && terminableVariables() && !hasLambdaProductions() && hasBinaryOrSimpleProductions();
    }

    private boolean hasBinaryOrSimpleProductions () {
        for (String production : productions.values()) {
            String [] productionsArray = production.split("\\|");
            for (String s : productionsArray) {
                if (s.length() > 2) {
                    return false;
                }else if (s.length() == 2 && s.equals(s.toUpperCase())) {
                    break;
                }else if (s.length() == 1 && s.equals(s.toLowerCase())) {
                    break;
                }else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean reachableVariables () {
        ArrayList<Character> unreachableVariables = toCharacterArrayList();
        for (char variable : productions.keySet()) {
            for (String production : productions.values()){
                if (!productions.get(variable).equals(production)) {
                    if (production.contains("" + variable)) {
                        unreachableVariables.remove(variable);
                        break;
                    }
                }
            }
        }
        return !unreachableVariables.isEmpty();
    }

    private boolean terminableVariables () {
        ArrayList<Character> generating = new ArrayList<>();
        for (char variable : productions.keySet()) {
            String [] production = productions.get(variable).split("\\|");
            for (String s : production) {
                if (s.equals(s.toLowerCase())) {
                    generating.add(variable);
                    break;
                }
            }
        }
        return generating.size() == variables.length;
    }

    private boolean hasLambdaProductions () {
        String initialProduction = productions.get(initialSymbol);
        for (String production : productions.values()) {
            if (production.contains("λ") && production.length() > 1 && !production.equals(initialProduction)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Character> toCharacterArrayList () {
        ArrayList<Character> charactersArrayList = new ArrayList<>();
        for (char variable : variables) {
            if (variable != initialSymbol)
                charactersArrayList.add(variable);
        }
        return charactersArrayList;
    }

}
