package org.ict.allaboutu.common;

import java.text.SimpleDateFormat;

public class FileNameChange {

    public static String change(String originalFileName, String formatStr) {
        String renameFileName = null;

        //바꿀 파일명에 대한 문자열 만들기
        //게시글 등록 요청 시점의 날짜정보를 이용함
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);

        //변경할 파일명 만들기
        renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));

        //원본 파일의 확장자를 추출해서, 바꿀 파일명에 붙여줌
        renameFileName += "." + originalFileName.substring(
                originalFileName.lastIndexOf(".") + 1);

        return renameFileName;
    }

}
