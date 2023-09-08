package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQL {
    List<Map<String, Object>> list = new ArrayList<>();

    private void insert() {
    }

    private void select(String data) {

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
            // kshvitaly vedmaky zaplatite chekanoy monetoy
            // chekanaya moonetka = 100rub

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

    private void update() {

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
                //tyt nichego net, ne beyte
                // idi svoey dorogoy stalker
            }

            if (oldValue > maxValue) {
                maxValue = oldValue;
            }
        }

        return maxValue;
    }
}

