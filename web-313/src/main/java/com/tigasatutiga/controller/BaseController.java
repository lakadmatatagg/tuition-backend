package com.tigasatutiga.controller;

import com.tigasatutiga.service.BaseSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.io.Serializable;
import java.util.Optional;

@Slf4j
public abstract class BaseController<E, M, ID extends Serializable> {

    protected BaseSO<E, M, ID> service;

    public BaseController(BaseSO<E, M, ID> service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public M findById(@PathVariable ID id) {
        M returnModel = null;
        try {
            Optional<M> m = service.findById(id);
            if (m.isPresent()) {
                returnModel = m.get();
            }
        } catch (Exception e) {
            log.error("ERROR BaseController.findById, {}", ExceptionUtils.getStackTrace(e));
        }
        return returnModel;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable ID id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("ERROR BaseController.delete, {}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody M model) {
        try {
            M returnModel = service.saveModel(model);
            return ResponseEntity.ok(returnModel); // return 200 OK with the saved model
        } catch (Exception e) {
            log.error("ERROR BaseController.save, {}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong");
        }
    }
}
