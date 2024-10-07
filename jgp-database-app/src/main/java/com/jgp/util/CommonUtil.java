package com.jgp.util;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author simiyu
 */
@Slf4j
public abstract class CommonUtil {

    public static final String NO_RESOURCE_FOUND_WITH_ID = "No resource found with Id";
    public static final String RESOURCE_CREATED = "Resource Created";
    public static final String RESOURCE_UPDATED = "Resource Updated";

    public static final String NO_FILE_TO_UPLOAD = "No File To Upload Was Found!";

    public static final String STATUS_CELL_IMPORTED = "Imported";

    private CommonUtil() {
    }

    public static String getCountyByCode(String code){
        return getKenyanCountiesMap().get(code);
    }

    public static Map<String, String> getKenyanCountiesMap(){
        Map<String, String> map = new HashMap<>();
        map.put("001", "Mombasa");
        map.put("002", "Kwale");
        map.put("003", "Kilifi");
        map.put("004", "Tana River");
        map.put("005", "Lamu");
        map.put("006", "Taita–Taveta");
        map.put("007", "Garissa");
        map.put("008", "Wajir");
        map.put("009", "Mandera");
        map.put("010", "Marsabit");
        map.put("011", "Isiolo");
        map.put("012", "Meru");
        map.put("013", "Tharaka-Nithi");
        map.put("014", "Embu");
        map.put("015", "Kitui");
        map.put("016", "Machakos");
        map.put("017", "Makueni");
        map.put("018", "Nyandarua");
        map.put("019", "Nyeri");
        map.put("020", "Kirinyaga");
        map.put("021", "Murang\'a");
        map.put("022", "Kiambu");
        map.put("023", "Turkana");
        map.put("024", "West Pokot");
        map.put("025", "Samburu");
        map.put("026", "Trans-Nzoia");
        map.put("027", "Uasin Gishu");
        map.put("028", "Elgeyo-Marakwet");
        map.put("029", "Nandi");
        map.put("030", "Baringo");
        map.put("031", "Laikipia");
        map.put("032", "Nakuru");
        map.put("033", "Narok");
        map.put("034", "Kajiado");
        map.put("035", "Kericho");
        map.put("036", "Bomet");
        map.put("037", "Kakamega");
        map.put("038", "Vihiga");
        map.put("039", "Bungoma");
        map.put("040", "Busia");
        map.put("041", "Siaya");
        map.put("042", "Kisumu");
        map.put("043", "Homa Bay");
        map.put("044", "Migori");
        map.put("045", "Kisii");
        map.put("046", "Nyamira");
        map.put("047", "Nairobi");
        return map;
    }


    @RequiredArgsConstructor
    @Getter
    public enum KenyanCounty {
        MOMBASA("001", "Mombasa"),
        KWALE("002", "Kwale"),
        KILIFI("003", "Kilifi"),
        TANA_RIVER("004", "Tana River"),
        LAMU("005", "Lamu"),
        TAITA_TAVETA("006", "Taita–Taveta"),
        GARISSA("007", "Garissa"),
        WAJIR("008", "Wajir"),
        MANDERA("009", "Mandera"),
        MARSABIT("010", "Marsabit"),
        ISIOLO("011", "Isiolo"),
        MERU("012", "Meru"),
        THARAKA_NITHI("013", "Tharaka-Nithi"),
        EMBU("014", "Embu"),
        KITUI("015", "Kitui"),
        MACHAKOS("016", "Machakos"),
        MAKUENI("017", "Makueni"),
        NYANDARUA("018", "Nyandarua"),
        NYERI("019", "Nyeri"),
        KIRINYAGA("020", "Kirinyaga"),
        MURANG_A("021", "Murang\'a"),
        KIAMBU("022", "Kiambu"),
        TURKANA("023", "Turkana"),
        WEST_POKOT("024", "West Pokot"),
        SAMBURU("025", "Samburu"),
        TRANS_NZOIA("026", "Trans-Nzoia"),
        UASIN_GISHU("027", "Uasin Gishu"),
        ELGEYO_MARAKWET("028", "Elgeyo-Marakwet"),
        NANDI("029", "Nandi"),
        BARINGO("030", "Baringo"),
        LAIKIPIA("031", "Laikipia"),
        NAKURU("032", "Nakuru"),
        NAROK("033", "Narok"),
        KAJIADO("034", "Kajiado"),
        KERICHO("035", "Kericho"),
        BOMET("036", "Bomet"),
        KAKAMEGA("037", "Kakamega"),
        VIHIGA("038", "Vihiga"),
        BUNGOMA("039", "Bungoma"),
        BUSIA("040", "Busia"),
        SIAYA("041", "Siaya"),
        KISUMU("042", "Kisumu"),
        HOMA_BAY("043", "Homa Bay"),
        MIGORI("044", "Migori"),
        KISII("045", "Kisii"),
        NYAMIRA("046", "Nyamira"),
        NAIROBI("047", "Nairobi"),
        UNKNOWN("999", "Unknown");

        private final String countyCode;
        private final String countyName;

        public static Optional<KenyanCounty> getKenyanCountyFromName(String name) {
            if (null == name) {
                return Optional.empty();
            }
            var county =  Arrays.stream(KenyanCounty.values())
                    .filter(kc -> {
                        final var startWith = kc.countyName.split("[^a-zA-Z0-9']")[0];
                        return name.equalsIgnoreCase(kc.countyName) || name.toUpperCase().startsWith(startWith.toUpperCase());
                    })
                    .findAny();
            return county.isPresent() ? county : Optional.of(KenyanCounty.UNKNOWN);
        }

        public static Optional<KenyanCounty> getKenyanCountyFromCode(String code) {
            if (null == code) {
                return Optional.empty();
            }
            var county =  Arrays.stream(KenyanCounty.values())
                    .filter(kc -> code.equals(kc.countyCode))
                    .findAny();
            return county.isPresent() ? county : Optional.of(KenyanCounty.UNKNOWN);
        }


    }
}
