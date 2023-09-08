package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SQL {
    List<Map<String, Object>> list = new ArrayList<>();

    public SQL() {
        start();
    }

    private void start() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter request");
        String value = input.next();

        switch (value.substring(0, 6).toLowerCase()) {
            case "select" -> select(value);
            case "update" -> update(value);
            case "delete" -> delete();
            case "insert" -> insert();
        }
    }
private void insert(String data) {
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

    private void select(String data) {
        data = data.replaceAll("select", "");
        data = data.replaceAll("VALUES", "");
        data = data.replaceAll("WHERE", "");
        data = data.replaceAll("'", "");

        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        StringBuilder comparison = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '=' || data.charAt(i) == '<' || data.charAt(i) == '>') {
                comparison.append(data.charAt(i));

                i++;

                while (i < data.length()) {
                    value.append(data.charAt(i));
                    i++;
                }
                break;
            }
            key.append(data.charAt(i));
        }

        if (comparison.toString().equals("=")) {
            for (Map<String, Object> row : list) {
                try {
                    if (row.containsKey(key.toString()) &
                            row.get(key.toString()).equals(Integer.parseInt(value.toString()))) {
                        System.out.println(row);
                    }
                } catch (NumberFormatException ea) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(value.toString())) {
                        System.out.println(row);
                    }
                }
            }
        }

        if (comparison.toString().equals("<")) {
            int oldIntValue = Integer.parseInt(value.toString());

            for (Map<String, Object> row : list) {
                for (int i = 0; i < oldIntValue; i++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(i)) {
                        System.out.println(row);
                    }
                }
            }
        }

        if (comparison.toString().equals(">")) {
            int oldIntValue = Integer.parseInt(value.toString());
            for (Map<String, Object> row : list) {
                for (int i = oldIntValue; i <= mapMaxValue(row); i++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(i)) {
                        System.out.println(row);
                        break;
                    }
                    oldIntValue++;
                }
            }
        }
    }


    private void delete() {
    }

    private void update(String value) {
        value = value.replaceAll("update", "");
        value = value.replaceAll(" ", "");
        value = value.replaceAll("'", "");
        value = value.replaceAll("VALUES", "");
        StringBuilder key = new StringBuilder();
        StringBuilder mapValue = new StringBuilder();
        StringBuilder oldKey = new StringBuilder();
        StringBuilder oldValue = new StringBuilder();
        StringBuilder comparison = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            while (value.charAt(i) != '=') {
                key.append(value.charAt(i));
                if (key.toString().equals("WHERE")) {
                    i++;
                    key.delete(0, 1000);

                    while (value.charAt(i) != '=' & value.charAt(i) != '<' & value.charAt(i) != '>') {
                        oldKey.append(value.charAt(i));
                        i++;
                    }
                    comparison.append(value.charAt(i));
                    i++;
                    while (value.charAt(i) != ',') {
                        oldValue.append(value.charAt(i));
                        i++;
                    }
                    break;
                }
                i++;
            }
            if (value.charAt(i) == '=') {
                i++;
                while (i < value.length()) {
                    mapValue.append(value.charAt(i));
                    i++;
                }
            }
        }
        if (comparison.toString().equals("=")) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                try {
                    if (row.containsKey(oldKey.toString()) & row.containsValue(Integer.parseInt(oldValue.toString()))) {
                        row.replace(key.toString(), mapValue);
                        list.set(i, row);
                    }
                } catch (NumberFormatException e) {
                    if (row.containsKey(oldKey.toString()) & row.containsValue(oldValue.toString())) {
                        row.replace(key.toString(), mapValue);
                        list.set(i, row);
                    }
                }
            }
        }
        if (comparison.toString().equals("<")) {
            int oldIntValue = Integer.parseInt(oldValue.toString());
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                for (int index = 0; index < oldIntValue; index++) {
                    if (row.containsKey(oldKey.toString()) & row.containsValue(index)) {
                        row.replace(key.toString(), mapValue);
                        list.set(i, row);
                    }
                }
            }
        }
        if (comparison.toString().equals(">")) {
            int oldIntValue;
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                oldIntValue = Integer.parseInt(oldValue.toString());
                for (int index = oldIntValue; index < mapMaxValue(row); index++) {
                    if (row.containsKey(oldKey.toString()) & row.get(oldKey.toString()).equals(oldIntValue)) {
                        row.replace(key.toString(), mapValue);
                        list.set(i, row);
                        break;
                    }
                    oldIntValue++;
                }
            }
        }
    }

    private int mapMaxValue(Map<String, Object> row) {
        String valve;
        int maxValue = 0;
        int oldValue = 0;

        List<Object> values = new ArrayList<>(row.values());

        for (Object value : values) {
            valve = value.toString();

            try {
                oldValue = Integer.parseInt(valve);
            } catch (NumberFormatException e) {
            }

            if (oldValue > maxValue) {
                maxValue = oldValue;
            }
        }

        return maxValue;
        }
    }

