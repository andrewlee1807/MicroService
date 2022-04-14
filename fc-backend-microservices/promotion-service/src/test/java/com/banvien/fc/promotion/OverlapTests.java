package com.banvien.fc.promotion;

import com.banvien.fc.promotion.dto.rules.Enum.Operation;
import com.banvien.fc.promotion.dto.rules.Enum.PromotionType;
import com.banvien.fc.promotion.dto.rules.PromotionRuleDTO;
import com.banvien.fc.promotion.entity.BlockProductEntity;
import com.banvien.fc.promotion.repository.BlockProductRepository;
import com.banvien.fc.promotion.utils.promotion.PromotionOverlapChecker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @noinspection Duplicates
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class OverlapTests {

    PromotionRuleDTO currentPromotion;
    PromotionRuleDTO newPromotion;

    @Before
    public void beforeAllMethod() {
        currentPromotion = new PromotionRuleDTO();
        newPromotion = new PromotionRuleDTO();
    }
//
//    @Autowired
//    BlockProductRepository repository;
//
//    @Test
//    public void test123() {
//        List<BlockProductEntity> kt = repository.findAdminBlockProduct(-1l, "", "", "");
//        int k = 10;
//    }

//    @Test
//    /**
//     * Test overlap Product vs Product
//     *
//     */
//    public void test() {
//        currentPromotion.setPromotionType(PromotionType.PRODUCT);
//        currentPromotion.setOperation(Operation.OR);
//        currentPromotion.getIds().add(10625L);
//        currentPromotion.getIds().add(10504L);
//
//        newPromotion.setPromotionType(PromotionType.PRODUCT);
//        newPromotion.setOperation(Operation.AND);
//        newPromotion.getIds().add(10486L);
//        newPromotion.getIds().add(10410L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    /**
//     * Test overlap Product vs Product
//     */
//    @Test
//    public void test1() {
//        currentPromotion.setPromotionType(PromotionType.PRODUCT);
//        currentPromotion.setOperation(Operation.OR);
//        currentPromotion.getIds().add(10625L);
//        currentPromotion.getIds().add(10504L);
//
//        newPromotion.setPromotionType(PromotionType.PRODUCT);
//        newPromotion.setOperation(Operation.AND);
//        newPromotion.getIds().add(10486L);
//        newPromotion.getIds().add(10504L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test2() {
//        currentPromotion.setPromotionType(PromotionType.PRODUCT);
//        currentPromotion.setOperation(Operation.AND);
//        currentPromotion.getIds().add(10625L);
//        currentPromotion.getIds().add(10504L);
//
//        newPromotion.setPromotionType(PromotionType.BRAND);
//        newPromotion.getIds().add(848L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test3() {
//        currentPromotion.setPromotionType(PromotionType.PRODUCT);
//        currentPromotion.setOperation(Operation.AND);
//        currentPromotion.getIds().add(10625L);
//        currentPromotion.getIds().add(10504L);
//
//        newPromotion.setPromotionType(PromotionType.BRAND);
//        newPromotion.getIds().add(848L);
//        newPromotion.getExcludeIds().add(10625L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    @Test
//    public void test4() {
//        currentPromotion.setPromotionType(PromotionType.BRAND);
//        currentPromotion.getIds().add(848L);
//        currentPromotion.getExcludeIds().add(10625L);
//
//        newPromotion.setPromotionType(PromotionType.PRODUCT);
//        newPromotion.setOperation(Operation.AND);
//        newPromotion.getIds().add(10504L);
//        newPromotion.getIds().add(10625L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    @Test
//    public void test5() {
//        currentPromotion.setPromotionType(PromotionType.BRAND);
//        currentPromotion.setOperation(Operation.AND);
//        currentPromotion.getIds().add(848L);
//        currentPromotion.getIds().add(862L);
//        currentPromotion.getExcludeIds().add(10504L);
//
//        newPromotion.setPromotionType(PromotionType.PRODUCT);
//        newPromotion.setOperation(Operation.AND);
//        newPromotion.getIds().add(10625L);
//        newPromotion.getIds().add(10410L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test6() {
//        currentPromotion.setPromotionType(PromotionType.BRAND);
//        currentPromotion.getIds().add(848L);
//        currentPromotion.getExcludeIds().add(9361L);
//
//        newPromotion.setPromotionType(PromotionType.BRAND);
//        newPromotion.setOperation(Operation.AND);
//        newPromotion.getIds().add(848L);
//        newPromotion.getIds().add(862L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test7() {
//        currentPromotion.setPromotionType(PromotionType.BRAND);
//        currentPromotion.getIds().add(848L);
//
//        newPromotion.setPromotionType(PromotionType.BRAND);
//        newPromotion.getIds().add(862L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    @Test
//    public void test8() {
//        currentPromotion.setPromotionType(PromotionType.PRODUCT);
//        currentPromotion.setOperation(Operation.AND);
//        currentPromotion.getIds().add(10625L);
//        currentPromotion.getIds().add(10504L);
//
//        newPromotion.setPromotionType(PromotionType.CATEGORY);
//        newPromotion.getIds().add(372L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test9() {
//        currentPromotion.setPromotionType(PromotionType.PRODUCT);
//        currentPromotion.setOperation(Operation.AND);
//        currentPromotion.getIds().add(10625L);
//        currentPromotion.getIds().add(10504L);
//
//        newPromotion.setPromotionType(PromotionType.CATEGORY);
//        newPromotion.getIds().add(372L);
//        newPromotion.getExcludeIds().add(10504L);
//        newPromotion.getExcludeIds().add(10486L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    @Test
//    public void test10() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.getIds().add(372L);
//        currentPromotion.getExcludeIds().add(10504L);
//
//        newPromotion.setPromotionType(PromotionType.PRODUCT);
//        newPromotion.setOperation(Operation.AND);
//        newPromotion.getIds().add(10504L);
//        newPromotion.getIds().add(10486L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test11() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.getIds().add(372L);
//        currentPromotion.getExcludeIds().add(10504L);
//        currentPromotion.getExcludeIds().add(10486L);
//
//        newPromotion.setPromotionType(PromotionType.PRODUCT);
//        newPromotion.setOperation(Operation.AND);
//        newPromotion.getIds().add(10504L);
//        newPromotion.getIds().add(10486L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    @Test
//    public void test12() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.getIds().add(372L);
//
//        newPromotion.setPromotionType(PromotionType.PRODUCT);
//        newPromotion.getIds().add(10625L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    @Test
//    public void test13() {
//        currentPromotion.setPromotionType(PromotionType.PRODUCT);
//        currentPromotion.setOperation(Operation.AND);
//        currentPromotion.getIds().add(10504L);
//        currentPromotion.getIds().add(10486L);
//        currentPromotion.getIds().add(10410L);
//
//        newPromotion.setPromotionType(PromotionType.CATEGORY);
//        newPromotion.getIds().add(367L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    @Test
//    public void test14() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.setOperation(Operation.AND);
//        currentPromotion.getIds().add(372L);
//        currentPromotion.getIds().add(367L);
//
//        newPromotion.setPromotionType(PromotionType.PRODUCT);
//        newPromotion.getIds().add(10625L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test15() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.getIds().add(360L);
//
//        newPromotion.setPromotionType(PromotionType.CATEGORY);
//        newPromotion.getIds().add(367L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test16() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.getIds().add(367L);
//
//        newPromotion.setPromotionType(PromotionType.CATEGORY);
//        newPromotion.getIds().add(361L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }
//
//    @Test
//    public void test17() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.setOperation(Operation.AND);
//        currentPromotion.getIds().add(367L);
//        currentPromotion.getIds().add(372L);
//
//        newPromotion.setPromotionType(PromotionType.CATEGORY);
//        newPromotion.getIds().add(372L);
//        newPromotion.getExcludeIds().add(10504L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test18() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.getIds().add(372L);
//
//        newPromotion.setPromotionType(PromotionType.BRAND);
//        newPromotion.getIds().add(862L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(true);
//    }
//
//    @Test
//    public void test19() {
//        currentPromotion.setPromotionType(PromotionType.CATEGORY);
//        currentPromotion.getIds().add(367L);
//
//        newPromotion.setPromotionType(PromotionType.BRAND);
//        newPromotion.getIds().add(862L);
//
//        boolean rs = PromotionOverlapChecker.isOverlap(currentPromotion, newPromotion);
//        assertThat(rs).isEqualTo(false);
//    }

}
