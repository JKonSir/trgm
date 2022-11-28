package org.example;

import java.util.Arrays;

public class Main {

    private static final double SIMILARITY_THRESHOLD = 0.3d;

    private static final byte LPADDING = 2;

    private static final byte RPADDING = 1;

    public static void main(String[] args) {
        System.out.println(similarity("test", "est"));
        System.out.println(similarity("dcb", "edcba"));
        System.out.println(similarity("tttcbbb", "aaaacbttttcbbbbabggggstssss"));
        System.out.println(similarity("cBA", "eDcBa?!@#$%^&*()+=\"№%:,.;/\\\\|`~[]{}-_<>0123456789"));
    }

    private static double similarity(String s1, String s2) {
        String[] trgm1 = generateTrgm(s1);
        String[] trgm2 = generateTrgm(s2);

        return countSimilarity(trgm1, trgm2);
    }

    private static String[] generateTrgm(String str) {
        String[] trgm = generateTrgmOnly(str);

        Arrays.sort(trgm);
        if (trgm.length > 1)
            trgm = deduplicate(trgm);

        return trgm;
    }

    private static String[] generateTrgmOnly(String s) {
        if (s.length() == 0 || s.length() + LPADDING + RPADDING < 3)
            return null;

        s = s.replaceAll("[^A-Za-z0-9]", " ")
                .toLowerCase();

        String[] trgms = new String[s.length() + 1];

        boolean newlexem = true;
        int i = 0;
        int j = 0;
        while (i < s.length()) {
            if (s.charAt(i) == ' ') {
                if (!newlexem)
                    trgms[j++] = s.substring(i - 2, i) + " ";
                newlexem = true;
                i++;
                continue;
            }
            if (newlexem) {
                newlexem = false;
                trgms[j++] = "  " + s.substring(i, ++i);
                trgms[j++] = " " + s.substring(i - 1, ++i);
            }
            trgms[j++] = s.substring(i - 2, ++i);
        }
        trgms[j] = s.substring(i - 2, i) + " ";

        String[] res = new String[++j];
        System.arraycopy(trgms, 0, res, 0, j);

        return res;
    }

    private static double countSimilarity(String[] trgm1, String[] trgm2) {
        if (trgm1 == null || trgm2 == null)
            return .0d;

        int count = 0;
        for (int i = 0, j = 0; i < trgm1.length && j < trgm2.length; ) {
            int res = trgm1[i].compareTo(trgm2[j]);

            if (res < 0)
                i++;
            else if (res > 0)
                j++;
            else {
                i++;
                j++;
                count++;
            }
        }

        return ((double) count) / ((double) (trgm1.length + trgm2.length - count));
    }

    private static String[] deduplicate(String[] array) {
        int count = 0;
        for (int i = 0; i < array.length - count - 1; i++) {
            for (int j = i + 1; j < array.length - count; j++) {
                if (!array[i].equals(array[j])) {
                    if (j > i + 1)
                        System.arraycopy(array, j, array, i + 1, array.length - j);
                    break;
                }
                count++;
            }
        }

        String[] res = new String[array.length - count];
        System.arraycopy(array, 0, res, 0, res.length);

        return res;
    }
}