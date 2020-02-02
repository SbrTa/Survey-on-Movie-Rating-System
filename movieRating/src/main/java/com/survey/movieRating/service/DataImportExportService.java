package com.survey.movieRating.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.movieRating.model.ExcelColumnDetails;
import com.survey.movieRating.model.MovieSourceModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
public class DataImportExportService {

    public List<MovieSourceModel> readFromExcel() {
        List<MovieSourceModel> movieSources = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:MovieList.xlsx");

            FileInputStream excelFile = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                MovieSourceModel movieSourceModel = new MovieSourceModel();
                boolean i = true;
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (i){
                        movieSourceModel.setName(currentCell.getStringCellValue());
                    }else {
                        movieSourceModel.setPath(currentCell.getStringCellValue());
                    }
                    i=false;
                }
                if (movieSourceModel.getName()!=null && movieSourceModel.getPath()!=null){
                    movieSources.add(movieSourceModel);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieSources;
    }


    public void exportAsExcel(HttpServletResponse response, String fileName, List<ExcelColumnDetails> columns, List<?> data) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");
        int rowCount = 0;
        int columnCount = 0;
        Row row = sheet.createRow(rowCount++);
        for (ExcelColumnDetails columnDetails : columns) {
            row.createCell(columnCount++).setCellValue(columnDetails.getHeader());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object obj : data) {
            row = sheet.createRow(rowCount++);
            Map<String, Object> rowData = objectMapper.convertValue(obj, Map.class);
            columnCount = 0;
            for (ExcelColumnDetails columnDetails : columns) {
                Cell cell = row.createCell(columnCount++);
                cell.setCellValue((String) rowData.get(columnDetails.getParameter()));
            }
        }
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}