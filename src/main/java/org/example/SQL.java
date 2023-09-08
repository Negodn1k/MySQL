package org.example;

import java.util.*;

public class SQL {
    List<Map<String, Object>> list = new ArrayList<>();

    public List<Map<String, Object>> start(String request) {
        StringBuilder function = new StringBuilder();
        StringBuilder data = new StringBuilder();
        request = request.replaceAll(" ", "");

        for (int i = 0; i < request.length(); i++) {
            if (i < 6) {
                function.append(request.charAt(i));
            } else {
                data.append(request.charAt(i));
            }
        }

        switch (function.toString()) {
            case "INSERT" -> insert(data.toString());
            //case "SELECT" -> select(data.toString());
            //case "DELETE" -> delete(data.toString());
            default -> System.out.println("Ne lomay programmy!");
        }
        System.out.println(list);
        return list;
    }

    private void insert(String data) {
        //INSERT VALUES key=data, key=data
        Map<String, Object> row = new HashMap<>();

        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();

        data.replaceAll("VALUES", "");
        data.replaceAll(" ", "");

        for (int i = 0; i != data.length(); i++){
            if (data.charAt(i) == '='){
                i++;

                while (data.charAt(i) != ','){
                    value.append(data.charAt(i));

                    i++;

                    if (i == data.length()){
                        break;
                    }
                }

                i--;
            } else if (data.charAt(i) == ',') {
                try{
                    row.put(key.toString(), Integer.parseInt(value.toString()));
                } catch (NumberFormatException e){
                    row.put(key.toString(), value.toString());
                }

                key.delete(0, 1000);
                value.delete(0, 1000);
            } else {
                key.append(data.charAt(i));
            }
        }

        try {
            row.put(key.toString(), Integer.parseInt(value.toString()));
        } catch (NumberFormatException e) {
            row.put(key.toString(), value.toString());
        }

        list.add(row);
    }

    private void select() {
    }

    private void delete() {
    }

    private void update(String value) {

    }

    private int mapMaxValue() {
        return 0;
    }
}
