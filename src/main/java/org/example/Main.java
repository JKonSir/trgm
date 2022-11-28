package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {

    private static final double SIMILARITY_THRESHOLD = 0.3d;

    private static final byte LPADDING = 2;
    private static final byte RPADDING = 1;

    public static void main(String[] args) {
        System.out.println(similarity("test", "est"));
        System.out.println(similarity("dcb", "edcba"));
        System.out.println(similarity("tttcbbb", "aaaacbttttcbbbbabggggstssss"));
//        System.out.println(8 * 0.3333333333333333 / (1 - 0.3333333333333333));
//        0.2857143 * (7 - count) = count; sum*t = count * (1 - t)

//        String[] trgm1 = { "  t", " tt", "ttt", "ttt", "tt "};
//        String[] trgm1 = generateTrgm("aaaacbttttcbbbbabggggstssss");
//        Arrays.sort(trgm1);
//        System.out.println(Arrays.asList(trgm1));
//        int count = 0;
//        for (int i = 0; i < trgm1.length - count - 1; i++) {
//            for (int j = i + 1; j < trgm1.length - count; j++) {
//                if (!trgm1[i].equals(trgm1[j])) {
//                    if (j > i + 1)
//                        System.arraycopy(trgm1, j, trgm1, i + 1, trgm1.length - j);
//                    break;
//                }
//                count++;
//            }
//        }
//        String[] res = new String[trgm1.length - count];
//        System.arraycopy(trgm1, 0, res, 0, res.length);
//        System.out.println(Arrays.asList(res));
    }

    private static double similarity(String s1, String s2) {
        String[] trgm1 = generateTrgmOnly(s1);
        String[] trgm2 = generateTrgmOnly(s2);

        Arrays.sort(trgm1);
        trgm1 = deduplicate(trgm1);

        Arrays.sort(trgm2);
        trgm2 = deduplicate(trgm2);

//        Set<String> tmpSet1 = new LinkedHashSet<>();
//        for (int i = 0; i < trgm1.length; i++) {
//            tmpSet1.add(trgm1[i]);
//        }
//        trgm1 = tmpSet1.toArray(new String[]{});
//
//        Set<String> tmpSet2 = new LinkedHashSet<>();
//        for (int i = 0; i < trgm2.length; i++) {
//            tmpSet2.add(trgm2[i]);
//        }
//        trgm2 = tmpSet2.toArray(new String[]{});

        return countSimilarity(trgm1, trgm2);
    }
//
//    private static generateTrgm();

//    private static String[] generateTrgm(String str) {
//        str.replaceAll("[^A-Za-z0-9]", " ");
//        for (String s1 : str.split(" ")) {
//            generateTrgmOnly(s1)
//        }
//
//        Arrays.f
//
//        Arrays.sort(trgm1);
//        trgm1 = deduplicate(trgm1);
//
//        s = s.replaceAll("[^A-Za-z0-9]", " ");
//    }

    private static String[] generateTrgmOnly(String s) {
        if (s.length() == 0 || s.length() + LPADDING + RPADDING < 3)
            return null;

        String[] trgms = new String[s.length() + 1];
        trgms[0] = "  " + s.charAt(0);
        trgms[1] = " " + s.substring(0, 2);
        trgms[trgms.length - 1] = s.substring(s.length() - 2) + " ";

        for (int i = 1; i < s.length() - 1; i++) {
            trgms[i + 1] = s.substring(i - 1, i + 2);
        }

        return trgms;
    }

    private static double countSimilarity(String[] trgm1, String[] trgm2) {
        if (trgm1 == null || trgm2 == null)
            return .0d;

        int count = 0;
        for (int i = 0, j = 0; i < trgm1.length && j < trgm2.length;) {
//            int res = compareTrgm(trgm1[i], trgm2[j]);
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

//    //{  t, te,est,st ,tes}
//    //{  e, es,est,st }
//
//    private static int compareTrgm(String s1, String s2) {
//        return compareChar(s1.charAt(0), s2.charAt(0)) != 0 ? compareChar(s1.charAt(0), s2.charAt(0)) :
//            compareChar(s1.charAt(1), s2.charAt(1)) != 0 ? compareChar(s1.charAt(1), s2.charAt(1)) : compareChar(s1.charAt(2), s2.charAt(2));
////        for (int i = 0; i < 3; i++) {
////
////        }
//    }
//
//    private static int compareChar(char c1, char c2) {
//        return c1 == c2 ? 0 : c1 < c2 ? -1 : 1;
////        return c1 == c2 ? 0 : c1 < c2 ? -1 : 1;
//    }

// #define CMPCHAR(a,b) ( ((a)==(b)) ? 0 : ( ((a)<(b)) ? -1 : 1 ) )
// #define CMPPCHAR(a,b,i)  CMPCHAR( *(((const char*)(a))+i), *(((const char*)(b))+i) )
// #define CMPTRGM(a,b) ( CMPPCHAR(a,b,0) ? CMPPCHAR(a,b,0) : ( CMPPCHAR(a,b,1) ? CMPPCHAR(a,b,1) : CMPPCHAR(a,b,2) ) )
}


//#define CALCSML	(	 	count,
//        len1,
//        len2
//        )		   ((float4) (count)) / ((float4) ((len1) + (len2) - (count)))

//#define VARDATA_ANY	(	 	PTR	)	    (VARATT_IS_1B(PTR) ? VARDATA_1B(PTR) : VARDATA_4B(PTR))

//(VARATT_IS_1B_E(PTR) ? VARSIZE_EXTERNAL(PTR)-VARHDRSZ_EXTERNAL : \
//        (VARATT_IS_1B(PTR) ? VARSIZE_1B(PTR)-VARHDRSZ_SHORT : \
//        VARSIZE_4B(PTR)-VARHDRSZ))


// Datum
//         1124 similarity(PG_FUNCTION_ARGS)
//         1125 {
//         1126     text       *in1 = PG_GETARG_TEXT_PP(0);
//         1127     text       *in2 = PG_GETARG_TEXT_PP(1);
//         1128     TRGM       *trg1,
//         1129                *trg2;
//         1130     float4      res;
//         1131
//         1132     trg1 = generate_trgm(VARDATA_ANY(in1), VARSIZE_ANY_EXHDR(in1));
//         1133     trg2 = generate_trgm(VARDATA_ANY(in2), VARSIZE_ANY_EXHDR(in2));
//         1134
//         1135     res = cnt_sml(trg1, trg2, false);
//         1136
//         1137     pfree(trg1);
//         1138     pfree(trg2);
//         1139     PG_FREE_IF_COPY(in1, 0);
//         1140     PG_FREE_IF_COPY(in2, 1);
//         1141
//         1142     PG_RETURN_FLOAT4(res);
//         1143 }

//TRGM *
//        358 generate_trgm(char *str, int slen)
//        359 {
//        360     TRGM       *trg;
//        361     int         len;
//        362
//        363     protect_out_of_mem(slen);
//        364
//        365     trg = (TRGM *) palloc(TRGMHDRSIZE + sizeof(trgm) * (slen / 2 + 1) * 3);
//        366     trg->flag = ARRKEY;
//        367
//        368     len = generate_trgm_only(GETARR(trg), str, slen, NULL);
//        369     SET_VARSIZE(trg, CALCGTSIZE(ARRKEY, len));
//        370
//        371     if (len == 0)
//        372         return trg;
//        373
//        374     /*
//  375      * Make trigrams unique.
//  376      */
//        377     if (len > 1)
//        378     {
//        379         qsort((void *) GETARR(trg), len, sizeof(trgm), comp_trgm);
//        380         len = qunique(GETARR(trg), len, sizeof(trgm), comp_trgm);
//        381     }
//        382
//        383     SET_VARSIZE(trg, CALCGTSIZE(ARRKEY, len));
//        384
//        385     return trg;
//        386 }


///*
//  268  * Make array of trigrams without sorting and removing duplicate items.
//  269  *
//  270  * trg: where to return the array of trigrams.
//  271  * str: source string, of length slen bytes.
//  272  * bounds: where to return bounds of trigrams (if needed).
//  273  *
//  274  * Returns length of the generated array.
//  275  */
//  276 static int
//        277 generate_trgm_only(trgm *trg, char *str, int slen, TrgmBound *bounds)
//        278 {
//        279     trgm       *tptr;
//        280     char       *buf;
//        281     int         charlen,
//        282                 bytelen;
//        283     char       *bword,
//        284                *eword;
//        285
//        286     if (slen + LPADDING + RPADDING < 3 || slen == 0)
//        287         return 0;
//        288
//        289     tptr = trg;
//        290
//        291     /* Allocate a buffer for case-folded, blank-padded words */
//        292     buf = (char *) palloc(slen * pg_database_encoding_max_length() + 4);
//        293
//        294     if (LPADDING > 0)
//        295     {
//        296         *buf = ' ';
//        297         if (LPADDING > 1)
//        298             *(buf + 1) = ' ';
//        299     }
//        300
//        301     eword = str;
//        302     while ((bword = find_word(eword, slen - (eword - str), &eword, &charlen)) != NULL)
//        303     {
//        304 #ifdef IGNORECASE
//        305         bword = lowerstr_with_len(bword, eword - bword);
//        306         bytelen = strlen(bword);
//        307 #else
//        308         bytelen = eword - bword;
//        309 #endif
//        310
//        311         memcpy(buf + LPADDING, bword, bytelen);
//        312
//        313 #ifdef IGNORECASE
//        314         pfree(bword);
//        315 #endif
//        316
//        317         buf[LPADDING + bytelen] = ' ';
//        318         buf[LPADDING + bytelen + 1] = ' ';
//        319
//        320         /* Calculate trigrams marking their bounds if needed */
//        321         if (bounds)
//        322             bounds[tptr - trg] |= TRGM_BOUND_LEFT;
//        323         tptr = make_trigrams(tptr, buf, bytelen + LPADDING + RPADDING,
//        324                              charlen + LPADDING + RPADDING);
//        325         if (bounds)
//        326             bounds[tptr - trg - 1] |= TRGM_BOUND_RIGHT;
//        327     }
//        328
//        329     pfree(buf);
//        330
//        331     return tptr - trg;
//        332 }


// /*
//  222  * Adds trigrams from words (already padded).
//  223  */
//  224 static trgm *
//        225 make_trigrams(trgm *tptr, char *str, int bytelen, int charlen)
//        226 {
//        227     char       *ptr = str;
//        228
//        229     if (charlen < 3)
//        230         return tptr;
//        231
//        232     if (bytelen > charlen)
//        233     {
//        234         /* Find multibyte character boundaries and apply compact_trigram */
//        235         int         lenfirst = pg_mblen(str),
//        236                     lenmiddle = pg_mblen(str + lenfirst),
//        237                     lenlast = pg_mblen(str + lenfirst + lenmiddle);
//        238
//        239         while ((ptr - str) + lenfirst + lenmiddle + lenlast <= bytelen)
//        240         {
//        241             compact_trigram(tptr, ptr, lenfirst + lenmiddle + lenlast);
//        242
//        243             ptr += lenfirst;
//        244             tptr++;
//        245
//        246             lenfirst = lenmiddle;
//        247             lenmiddle = lenlast;
//        248             lenlast = pg_mblen(ptr + lenfirst + lenmiddle);
//        249         }
//        250     }
//        251     else
//        252     {
//        253         /* Fast path when there are no multibyte characters */
//        254         Assert(bytelen == charlen);
//        255
//        256         while (ptr - str < bytelen - 2 /* number of trigrams = strlen - 2 */ )
//        257         {
//        258             CPTRGM(tptr, ptr);
//        259             ptr++;
//        260             tptr++;
//        261         }
//        262     }
//        263
//        264     return tptr;
//        265 }



// float4
//         998 cnt_sml(TRGM *trg1, TRGM *trg2, bool inexact)
//         999 {
//         1000     trgm       *ptr1,
//         1001                *ptr2;
//         1002     int         count = 0;
//         1003     int         len1,
//         1004                 len2;
//         1005
//         1006     ptr1 = GETARR(trg1);
//         1007     ptr2 = GETARR(trg2);
//         1008
//         1009     len1 = ARRNELEM(trg1);
//         1010     len2 = ARRNELEM(trg2);
//         1011
//         1012     /* explicit test is needed to avoid 0/0 division when both lengths are 0 */
//         1013     if (len1 <= 0 || len2 <= 0)
//         1014         return (float4) 0.0;
//         1015
//         1016     while (ptr1 - GETARR(trg1) < len1 && ptr2 - GETARR(trg2) < len2)
//        1017     {
//        1018         int         res = CMPTRGM(ptr1, ptr2);
//        1019
//        1020         if (res < 0)
//        1021             ptr1++;
//        1022         else if (res > 0)
//        1023             ptr2++;
//        1024         else
//        1025         {
//        1026             ptr1++;
//        1027             ptr2++;
//        1028             count++;
//        1029         }
//        1030     }
//        1031
//        1032     /*
// 1033      * If inexact then len2 is equal to count, because we don't know actual
// 1034      * length of second string in inexact search and we can assume that count
// 1035      * is a lower bound of len2.
// 1036      */
//        1037     return CALCSML(count, len1, inexact ? count : len2);
//        1038 }


// #define CMPCHAR(a,b) ( ((a)==(b)) ? 0 : ( ((a)<(b)) ? -1 : 1 ) )
//        00040 #define CMPPCHAR(a,b,i)  CMPCHAR( *(((const char*)(a))+i), *(((const char*)(b))+i) )
//        00041 #define CMPTRGM(a,b) ( CMPPCHAR(a,b,0) ? CMPPCHAR(a,b,0) : ( CMPPCHAR(a,b,1) ? CMPPCHAR(a,b,1) : CMPPCHAR(a,b,2) ) )
