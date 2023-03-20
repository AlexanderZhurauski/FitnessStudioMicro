package org.mycompany.report.service;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mycompany.report.core.dto.audit.AuditDTO;
import org.mycompany.report.core.dto.audit.AuditUserDTO;
import org.mycompany.report.service.api.IExcelService;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelService implements IExcelService {

    private static final String ROW_ID = "row_id";
    private static final String CREATED_AT = "created_at";
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "user_email";
    private static final String FULL_NAME = "full_name";
    private static final String USER_ROLE = "user_role";
    private static final String DESCRIPTION = "description";
    private static final String ENTITY_MODIFIED = "entity_modified";
    private static final String ENTITY_ID = "entity_id";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public InputStreamResource convertToExcel(List<AuditDTO> auditData) throws IOException {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()){

            Sheet sheet = workbook.createSheet();
            createHeaderRow(sheet);
            int rowNum = 1;

            for (AuditDTO auditRow : auditData) {
                Row currentRow = sheet.createRow(rowNum);
                AuditUserDTO userData = auditRow.getUser();
                currentRow.createCell(0, CellType.STRING).setCellValue(auditRow.getId());
                currentRow.createCell(1, CellType.STRING).setCellValue(instantToString(auditRow.getCreationTime()));
                currentRow.createCell(2, CellType.STRING).setCellValue(userData.getUuid().toString());
                currentRow.createCell(3, CellType.STRING).setCellValue(userData.getMail());
                currentRow.createCell(4, CellType.STRING).setCellValue(userData.getFio());
                currentRow.createCell(5, CellType.STRING).setCellValue(userData.getRole().toString());
                currentRow.createCell(6, CellType.STRING).setCellValue(auditRow.getText());
                currentRow.createCell(7, CellType.STRING).setCellValue(auditRow.getType().toString());
                currentRow.createCell(8, CellType.STRING).setCellValue(auditRow.getId());
                rowNum++;
            }
            setAutosize(sheet);
            workbook.write(out);

            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        } catch (IOException e) {
            throw new IOException("There has been an error in creating the excel report", e);
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0, CellType.STRING).setCellValue(ROW_ID);
        headerRow.createCell(1, CellType.STRING).setCellValue(CREATED_AT);
        headerRow.createCell(2, CellType.STRING).setCellValue(USER_ID);
        headerRow.createCell(3, CellType.STRING).setCellValue(USER_EMAIL);
        headerRow.createCell(4, CellType.STRING).setCellValue(FULL_NAME);
        headerRow.createCell(5, CellType.STRING).setCellValue(USER_ROLE);
        headerRow.createCell(6, CellType.STRING).setCellValue(DESCRIPTION);
        headerRow.createCell(7, CellType.STRING).setCellValue(ENTITY_MODIFIED);
        headerRow.createCell(8, CellType.STRING).setCellValue(ENTITY_ID);
    }

    private String instantToString(Instant instant) {

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    private void setAutosize(Sheet sheet) {
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
