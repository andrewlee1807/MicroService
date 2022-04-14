package com.banvien.fc.delivery.controller;

import com.banvien.core.sdk.dr.integration.vrp.call.Caller;
import com.banvien.core.sdk.dr.integration.vrp.call.DefaultCaller;
import com.banvien.core.sdk.dr.integration.vrp.domain.VehicleRoutingSolution;
import com.banvien.core.sdk.dr.integration.vrp.domain.dto.CustomerDTO;
import com.banvien.core.sdk.dr.integration.vrp.domain.dto.VehicleRoutingSolutionDTO;
import com.banvien.core.sdk.dr.integration.vrp.service.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery/dynamic-routing")
public class DynamicRoutingController {

    @PostMapping(path = "/get-routes")
    public ResponseEntity<Response<Map<String, List<CustomerDTO>>, Map<String, List<Object>>>> getRoutes(
            @Valid @RequestBody final VehicleRoutingSolutionDTO vehicleRoutingSolutionDTO) {
        VehicleRoutingSolution solution = null;
        Response<Map<String, List<CustomerDTO>>, Map<String, List<Object>>> response;
        if (vehicleRoutingSolutionDTO != null) {
            solution = VehicleRoutingSolution.toEntity(vehicleRoutingSolutionDTO);
        }
        Caller<VehicleRoutingSolution, Map<String, List<CustomerDTO>>, Map<String, List<Object>>> caller = new DefaultCaller();
        response = caller.startSolver(solution);
        return ResponseEntity.ok(response);
    }

}
