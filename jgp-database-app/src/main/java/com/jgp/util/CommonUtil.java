package com.jgp.util;

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
}
