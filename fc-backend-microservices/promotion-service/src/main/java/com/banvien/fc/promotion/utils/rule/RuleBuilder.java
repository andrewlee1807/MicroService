package com.banvien.fc.promotion.utils.rule;

import com.banvien.fc.promotion.dto.rules.DiscountDTO;
import com.banvien.fc.promotion.dto.rules.Enum.Operation;
import com.banvien.fc.promotion.dto.rules.Enum.PromotionType;
import com.banvien.fc.promotion.dto.rules.PromotionRuleDTO;
import com.google.gson.annotations.JsonAdapter;
import io.opencensus.resource.Resource;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @noinspection ALL
 */
public class RuleBuilder {

    public static String createRuleTest(PromotionRuleDTO dto) {
        try {

            if (dto.getDiscountType() == null) dto.setDiscountType(dto.getDiscounts().get(0).getDiscountType());
            String template = IOUtils.toString(RuleBuilder.class.getClassLoader().getResourceAsStream("ruleTemplate.json"));
//            String template = new String(Files.readAllBytes(Paths.get(RuleBuilder.class.getClassLoader().getResource("ruleTemplate.json").toURI())));
            String rule = template
                    .replace("{{promotionType}}", dto.getPromotionType().getValue())
                    .replace("{{ids}}", (new JSONArray(dto.getIds()).toString()))
                    .replace("{{exclude}}", (new JSONArray(dto.getExcludeIds()).toString()))
                    .replace("{{minDiscount}}", String.valueOf(dto.getDiscounts().get(0).getMinValue()))
                    .replace("{{baseOn}}", dto.getBaseOn().getValue())
                    .replace("{{operation}}", dto.getOperation().getValue())
                    .replace("{{discountDtos}}", (new ObjectMapper().writeValueAsString(dto.getDiscounts())))
                    .replace("{{discountType}}", dto.getDiscountType().getValue());
            if (dto.getPromotionType() == PromotionType.ORDER || dto.getPromotionType() == PromotionType.SHIPPING) {
                rule.replace("{\"var\": \"isByPass\"}", "true");
            }
            return (new JSONObject(rule)).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String createRule(PromotionRuleDTO dto) {
        if (dto == null) return "";
        if (dto.getExcludeIds() == null) {
            dto.setExcludeIds(new ArrayList<>());
        }
        switch (dto.getPromotionType()) {
            case CATEGORY:
                return createRuleForProduct(dto);
            case PRODUCT:
                return createRuleForProduct(dto);
            case BRAND:
                return createRuleForProduct(dto);
            case SHIPPING:
                return createRuleForShipping(dto);
            default:
                return "";
        }
    }

    private static String createRuleForShipping(PromotionRuleDTO dto) {
        StringBuilder rule = new StringBuilder();
        rule.append("{\"out\":[");

        for (DiscountDTO item : dto.getDiscounts()) {
            rule.append("{\"if\":[{\">=\":[{\"var\":\"totalQuantity\"},\"");
            rule.append(item.getMinValue());
            rule.append("\"]},");
        }

        for (int i = dto.getDiscounts().size() - 1; i >= 0; i--) {
            rule.append("\"");
            rule.append(dto.getDiscounts().get(i).getMinValue());
            if (i == dto.getDiscounts().size() - 1) {
                rule.append("\",");
            } else {
                rule.append("\"]},");
            }
        }
        rule.append("\"null\"]},");

        for (DiscountDTO item : dto.getDiscounts()) {
            rule.append("{\"if\":[{\">=\":[{\"var\":\"totalQuantity\"},\"");
            rule.append(item.getMinValue());
            rule.append("\"]},");
        }

        for (int i = dto.getDiscounts().size() - 1; i >= 0; i--) {
            rule.append("\"");
            rule.append(dto.getDiscounts().get(i).getDiscountValue());
            if (i == dto.getDiscounts().size() - 1) {
                rule.append("\",");
            } else {
                rule.append("\"]},");
            }
        }
        rule.append("\"null\"]},");

        rule.append("\"");
        rule.append(dto.getDiscounts().get(0).getDiscountType().getValue());
        rule.append("\",\"\"]}");

        return rule.toString();
    }

    private static String createRuleForProduct(PromotionRuleDTO dto) {
        //operation
        StringBuilder rule = new StringBuilder();
        rule.append("{\"if\":[{\"and\":[{\"");
        rule.append(dto.getOperation().getValue());
        rule.append("\":[");

        //condition
        Object[] ids = dto.getIds().toArray();
        for (int i = 0; i < ids.length; i++) {
            rule.append("{\">\":[{\"reduce\":[{\"filter\":[{\"var\":\"products\"},{\"and\":[{\"==\":[{\"var\":\"");
            rule.append(dto.getPromotionType().getValue());
            rule.append("\"},\"");
            rule.append(ids[i]);
            rule.append("\"]}");
            for (Long item : dto.getExcludeIds()) {
                rule.append(",{\"!=\":[{\"var\":\"id\"},\"");
                rule.append(item);
                rule.append("\"]}");
            }
            rule.append("]}]},{\"+\":[{\"var\":\"current.quantity\"},{\"var\":\"accumulator\"}]},0]},0]}");
            if (i != ids.length - 1) rule.append(",");
        }
        rule.append("]},");
        rule.append("{\">=\":[{\"reduce\":[{\"filter\":[{\"var\":\"products\"},{\"and\":[{\"or\":[");

        for (int i = 0; i < ids.length; i++) {
            rule.append("{\"==\":[{\"var\":\"");
            rule.append(dto.getPromotionType().getValue());
            rule.append("\"},\"");
            rule.append(ids[i]);
            rule.append("\"]}");
            if (i != ids.length - 1) rule.append(",");
        }
        rule.append("]}");

        //exclude
        for (Long item : dto.getExcludeIds()) {
            rule.append(",{\"!=\":[{\"var\":\"id\"},\"");
            rule.append(item);
            rule.append("\"]}");
        }

        //check valid true/false
        rule.append("]}]},{\"+\":[{\"var\":\"current.");
        rule.append(dto.getBaseOn().getValue());
        rule.append("\"},{\"var\":\"accumulator\"}]},0]},");
        rule.append(dto.getDiscounts().get(0).getMinValue());
        rule.append("]}]},{\"out\":[");

        //create output (DiscountDTO)
        for (DiscountDTO item : dto.getDiscounts()) {
            rule.append("{\"if\":[{\">=\":[{\"var\":\"total" + dto.getBaseOn().getValueUper() + "\"},\"");
            rule.append(item.getMinValue());
            rule.append("\"]},");
        }

        for (int i = dto.getDiscounts().size() - 1; i >= 0; i--) {
            rule.append("\"");
            rule.append(dto.getDiscounts().get(i).getMinValue());
            if (i == dto.getDiscounts().size() - 1) {
                rule.append("\",");
            } else {
                rule.append("\"]},");
            }
        }
        rule.append("\"null\"]},");

        for (DiscountDTO item : dto.getDiscounts()) {
            rule.append("{\"if\":[{\">=\":[{\"var\":\"total" + dto.getBaseOn().getValueUper() + "\"},\"");
            rule.append(item.getMinValue());
            rule.append("\"]},");
        }

        for (int i = dto.getDiscounts().size() - 1; i >= 0; i--) {
            rule.append("\"");
            rule.append(dto.getDiscounts().get(i).getDiscountValue());
            if (i == dto.getDiscounts().size() - 1) {
                rule.append("\",");
            } else {
                rule.append("\"]},");
            }
        }
        rule.append("\"null\"]},");

        rule.append("\"");
        rule.append(dto.getDiscounts().get(0).getDiscountType().getValue());
        rule.append("\",");

        for (DiscountDTO item : dto.getDiscounts()) {
            rule.append("{\"if\":[{\">=\":[{\"var\":\"total" + dto.getBaseOn().getValueUper() + "\"},\"");
            rule.append(item.getMinValue());
            rule.append("\"]},");
        }

        // target promotion
        for (int i = dto.getDiscounts().size() - 1; i >= 0; i--) {
            rule.append("[");
            for (int j = 0; j < dto.getDiscounts().get(i).getTargetIds().size(); j++) {
                rule.append(dto.getDiscounts().get(i).getTargetIds().get(j));
                if (j != dto.getDiscounts().get(i).getTargetIds().size() - 1) {
                    rule.append(",");
                }
            }
            if (i == dto.getDiscounts().size() - 1) {
                rule.append("],");
            } else {
                rule.append("]]},");
            }
        }
        rule.append("\"null\"]}]},\"null\"]}");
        return rule.toString();
    }
}
