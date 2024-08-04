package com.jgp.infrastructure.bulkimport.constants;

public class TemplatePopulateImportConstants {

    private TemplatePopulateImportConstants() {
    }

    public static final String BMO_SHEET_NAME = "bmos";

    public static final int ROWHEADER_INDEX = 0;
    public static final short ROW_HEADER_HEIGHT = 500;
    public static final int FIRST_COLUMN_INDEX = 0;

    // Status column
    public static final String STATUS_CELL_IMPORTED = "Imported";
    public static final String STATUS_CREATION_FAILED = "Creation failed";
    public static final String STATUS_APPROVAL_FAILED = "Approval failed";
    public static final String STATUS_ACTIVATION_FAILED = "Activation failed";
    public static final String STATUS_MEETING_FAILED = "Meeting failed";
    public static final String STATUS_DISBURSAL_FAILED = "Disbursal failed";
    public static final String STATUS_DISBURSAL_REPAYMENT_FAILED = "Repayment failed";
    public static final String STATUS_COLUMN_HEADER = "Status";

    public static final String STATUS_COL_REPORT_HEADER = "Status";
    public static final String FAILURE_COL_REPORT_HEADER = "Failure Report";

    // Entity Types
    public static final String BMO_ENTITY = "BMO_ENTITY";
}
