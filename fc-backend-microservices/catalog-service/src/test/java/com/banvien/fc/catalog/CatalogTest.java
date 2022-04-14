package com.banvien.fc.catalog;

import com.banvien.fc.catalog.dto.RawBrandDTO;
import com.banvien.fc.catalog.dto.RawDistributorDTO;
import com.banvien.fc.catalog.dto.UserDTO;
import com.banvien.fc.catalog.repository.*;
import com.banvien.fc.catalog.service.SalesFlowService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class CatalogTest {

    @Test
    void test2() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("type","Products");
        JSONObject jsonObject = salesFlowService.sendRequest("/master-data", params);
        ObjectMapper mapper = new ObjectMapper();
        List<RawBrandDTO> RawBrandDTOS = mapper.readValue(jsonObject.get("data").toString(), new TypeReference<List<RawBrandDTO>>(){});
        int k = 10;
    }

    @Test
    void test() throws Exception {
        salesFlowService.updateDistributorMasterData();
        salesFlowService.updateProductMasterData();
        salesFlowService.updateStoreMasterData();
    }

    @Test
    void test7() throws Exception {
//        Map<String, Object> params = new HashMap<>();
//        params.put("type","Brands");
//        String rp = salesFlowService.sendRequest("/master-data", params);
        JSONObject jsonObject = new JSONObject("{\n" +
                "    \"response\": \"SUCCESS\",\n" +
                "    \"message\": \"\",\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"DistributorCode\": \"D00001\",\n" +
                "            \"DistributorName\": \"Distributor 1\",\n" +
                "            \"DistributorPhone\": \"03001234567\",\n" +
                "            \"Status\": 1,\n" +
                "            \"DistributorAddress\": \"Karachi, Pakistan\",\n" +
                "            \"Latitude\": 30.3753,\n" +
                "            \"Longitude\": 69.3451\n" +
                "        },\n" +
                "        {\n" +
                "            \"DistributorCode\": \"D00002\",\n" +
                "            \"DistributorName\": \"Distributor 2\",\n" +
                "            \"DistributorPhone\": \"03001234567\",\n" +
                "            \"Status\": 1,\n" +
                "            \"DistributorAddress\": \"Karachi, Pakistan\",\n" +
                "            \"Latitude\": 30.3753,\n" +
                "            \"Longitude\": 69.3451\n" +
                "        }\n" +
                "    ]\n" +
                "}");
//        JSONObject jsonObject = new JSONObject(rp);
        ObjectMapper mapper = new ObjectMapper();
        String body = jsonObject.get("data").toString();
        List<RawDistributorDTO> RawBrandDTOS = mapper.readValue(body, new TypeReference<List<RawDistributorDTO>>(){});
        int k = 10;
//        salesFlowService.updateProductMasterData();
//        salesFlowService.updateDistributorMasterData();
    }

    @Test
    void test3() {
        UserDTO wholesalerDto = new UserDTO();
        wholesalerDto.setFullPhoneNumber("0214444533465");
        wholesalerDto.setCountryId(23L);
        ResponseEntity<UserDTO> wholesaler = accountRepository.addOrUpdateUser(wholesalerDto, 23L);
        Long a = wholesaler.getBody().getUserId();
        System.out.println("------------------------------------------------------");
        System.out.println(a);
    }

    @Test
    void test4() {
//        Object maxNum = outletRepository.findMaxWSCode(23L);
//        String a = String.format( "WS%05d", (maxNum != null ? Integer.parseInt(maxNum.toString()) : 0) + 1);
//        System.out.println("------------------------------------------------------");
//        System.out.println(a);
        productOutletRepository.deActiveProductOutletByCountryId(23L, new Timestamp(System.currentTimeMillis()));
//        productRepository.deActiveProductByCountryId(23L, new Timestamp(System.currentTimeMillis()));
    }

    @Autowired
    private SalesFlowService salesFlowService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OutletRepository outletRepository;
    @Autowired
    private ProductOutletRepository productOutletRepository;
    @Autowired
    private ProductRepository productRepository;
}
