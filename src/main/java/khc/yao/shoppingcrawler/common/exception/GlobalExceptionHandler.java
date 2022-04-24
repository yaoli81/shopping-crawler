package khc.yao.shoppingcrawler.common.exception;

import khc.yao.shoppingcrawler.common.dto.BasicResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 接受所有沒特別聲明處理的例外
    @ExceptionHandler(Exception.class)
    public BasicResponseDTO any(Exception e) {
        BasicResponseDTO responseDTO = new BasicResponseDTO();
        log.error("Global Error", e);
        return responseDTO;
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public BasicResponseDTO any(ClassNotFoundException e) {
        BasicResponseDTO responseDTO = new BasicResponseDTO();
        responseDTO.setMessage("請確認網站名稱是否正確");
        return responseDTO;
    }
}
