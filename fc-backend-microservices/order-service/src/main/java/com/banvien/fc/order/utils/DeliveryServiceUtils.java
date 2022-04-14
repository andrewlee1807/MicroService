package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.OutletDTO;
import com.banvien.fc.order.dto.delivery.DeliveryServiceDTO;
import com.banvien.fc.order.entity.DeliveryServiceEntity;
import com.banvien.fc.order.entity.OutletEntity;
import com.banvien.fc.order.repository.DeliveryServiceRepository;

import java.util.ArrayList;
import java.util.List;

public class DeliveryServiceUtils {

    public static DeliveryServiceEntity dto2Entity(DeliveryServiceDTO dto) {
        DeliveryServiceEntity entity = new DeliveryServiceEntity();
        entity.setDeliveryServiceId(dto.getDeliveryServiceId());
        entity.setOutletId(dto.getOutlet().getOutletId());
        entity.setCode(dto.getCode());
        entity.setGroupCode(dto.getGroupCode());
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        boolean status = dto.getStatus() != null ? dto.getStatus() : false;
        entity.setStatus(status);
        entity.setOrderBy(dto.getOrderBy());
        entity.setDistance(dto.getDistance());
        entity.setEstimateDays(dto.getEstimateDays());
        return entity;
    }

    public static List<DeliveryServiceEntity> dtoList2EntityList(List<DeliveryServiceDTO> dtoList) {
        if (dtoList.size() == 0) {
            return null;
        }
        List<DeliveryServiceEntity> entityList = new ArrayList<>();
        for (DeliveryServiceDTO dto : dtoList) {
            DeliveryServiceEntity entity = dto2Entity(dto);
            entityList.add(entity);
        }

        return entityList;
    }

    public static DeliveryServiceDTO entity2Dto(DeliveryServiceEntity entity) {
        DeliveryServiceDTO dto = new DeliveryServiceDTO();
        dto.setDeliveryServiceId(entity.getDeliveryServiceId());
        OutletDTO outletDTO = new OutletDTO();
        outletDTO.setOutletId(entity.getOutletId());
        dto.setOutlet(outletDTO);
        dto.setCode(entity.getCode());
        dto.setGroupCode(entity.getGroupCode());
        dto.setTitle(entity.getTitle());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());
        dto.setOrderBy(entity.getOrderBy());
        dto.setDistance(entity.getDistance());
        dto.setEstimateDays(entity.getEstimateDays());
        return dto;
    }

    public static List<DeliveryServiceDTO> entityList2DtoList(List<DeliveryServiceEntity> entityList) {
        if (entityList.size() == 0) {
            return null;
        }
        List<DeliveryServiceDTO> dtoList = new ArrayList<>();
        for (DeliveryServiceEntity entity : entityList) {
            DeliveryServiceDTO dto = entity2Dto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * Calculate Delivery price for delivery method group by "code".
     *
     * @param deliveryServiceEntities
     * @param distance
     * @return
     * @note: param deliveryServiceEntities must to find by method findByOutletAndCode - it will return list order by distance
     */
    public static Double calculateDeliveryPrice(List<DeliveryServiceEntity> deliveryServiceEntities, Double distance) {
        Double deliveryPrice = 0D;
        // 1.find delivery method
        // List<DeliveryServiceEntity> deliveryServiceEntities  = deliveryServiceLocalBean.findByOutletAndCode(outletId, deliveryCode); // order by distance min -> max
        int deliveryServiceSize = deliveryServiceEntities.size();

        if (distance == null || distance.isNaN()) {
            distance = 0D;
        }

        // 2.get price from it
        if (deliveryServiceSize > 0) {
            // Find the min-max distance number that store set up.
            Double minDistance = deliveryServiceEntities.get(0).getDistance();
            Double maxDistance = deliveryServiceEntities.get(deliveryServiceSize - 1).getDistance();
            if (minDistance == null || minDistance.isNaN()) {
                minDistance = 0D;
            }
            if (maxDistance == null || maxDistance.isNaN()) {
                maxDistance = 0D;
            }
            if (distance < minDistance) {
                deliveryPrice = deliveryServiceEntities.get(0).getPrice() != null ? deliveryServiceEntities.get(0).getPrice() : 0d;
            } else if (distance >= maxDistance) {
                deliveryPrice = deliveryServiceEntities.get(deliveryServiceSize - 1).getPrice();
                if (deliveryPrice == null) {
                    deliveryPrice = 0D;
                }
            } else {
                // note: make sure list delivery always sort
                for (DeliveryServiceEntity deliveryServiceEntity : deliveryServiceEntities) {
                    if (distance <= deliveryServiceEntity.getDistance()) {
                        deliveryPrice = deliveryServiceEntity.getPrice();
                        break;
                    }
                }
            }
        }
        // 3. return price
        return deliveryPrice;
    }

    public static DeliveryServiceEntity calculateDeliveryPrice(List<DeliveryServiceEntity> deliveryServiceEntities, Double distance, DeliveryServiceEntity entity) {
        Double deliveryPrice = 0D;
        // 1.find delivery method
        // List<DeliveryServiceEntity> deliveryServiceEntities  = deliveryServiceLocalBean.findByOutletAndCode(outletId, deliveryCode); // order by distance min -> max
        int deliveryServiceSize = deliveryServiceEntities.size();

        if (distance == null || distance.isNaN()) {
            distance = 0D;
        }

        // 2.get price from it
        if (deliveryServiceSize > 0) {
            // Find the min-max distance number that store set up.
            Double minDistance = deliveryServiceEntities.get(0).getDistance();
            Double maxDistance = deliveryServiceEntities.get(deliveryServiceSize - 1).getDistance();
            if (minDistance == null || minDistance.isNaN()) {
                minDistance = 0D;
            }
            if (maxDistance == null || maxDistance.isNaN()) {
                maxDistance = 0D;
            }
            if (distance < minDistance) {
                deliveryPrice = deliveryServiceEntities.get(0).getPrice() != null ? deliveryServiceEntities.get(0).getPrice() : 0d;
            } else if (distance >= maxDistance) {
                deliveryPrice = deliveryServiceEntities.get(deliveryServiceSize - 1).getPrice();
                if (deliveryPrice == null) {
                    deliveryPrice = 0D;
                }
            } else {
                // note: make sure list delivery always sort
                for (DeliveryServiceEntity deliveryServiceEntity : deliveryServiceEntities) {
                    if (distance <= deliveryServiceEntity.getDistance()) {
                        deliveryPrice = deliveryServiceEntity.getPrice();
                        break;
                    }
                }
            }
            entity.setDistance(maxDistance != null ? maxDistance : minDistance != null ? minDistance : 0d);
        }

        entity.setPrice(deliveryPrice);
        // 3. return price
        return entity;
    }

    public static List<DeliveryServiceEntity> createDeliveryServiceDefault(DeliveryServiceRepository deliveryServiceRepository, Long outletId, List<DeliveryServiceEntity> deliveryServices) {
        List<DeliveryServiceEntity> newDeliveryServices = new ArrayList<>();

        List<String> deliveryServiceCodes = new ArrayList<>();
        for (DeliveryServiceEntity entity : deliveryServices) {
            deliveryServiceCodes.add(entity.getCode());
        }
        OutletEntity outletEntity = new OutletEntity();
        outletEntity.setOutletId(outletId);

        // Get from outlet
        if (!deliveryServiceCodes.contains(CoreConstants.DELIVERY_PICK_N_GO)) {
            DeliveryServiceEntity deliveryServiceEntity1 = new DeliveryServiceEntity();
            deliveryServiceEntity1.setOutletId(outletEntity.getOutletId());
            deliveryServiceEntity1.setGroupCode(CoreConstants.DELIVERY_PICK_N_GO);
            deliveryServiceEntity1.setCode(CoreConstants.DELIVERY_PICK_N_GO);
            deliveryServiceEntity1.setTitle(CoreConstants.DELIVERY_PICK_N_GO_TITLE);
            deliveryServiceEntity1.setPrice(0D);
            deliveryServiceEntity1.setStatus(true);
            deliveryServiceEntity1.setDistance(0D);
            deliveryServiceEntity1.setOrderBy(0);
            newDeliveryServices.add(deliveryServiceRepository.save(deliveryServiceEntity1));
        }

        // standard delivery (SHIP_TO_HOME)
        if (!deliveryServiceCodes.contains(CoreConstants.DELIVERY_PAY_N_GET_CODE)) {
            DeliveryServiceEntity deliveryServiceEntity2 = new DeliveryServiceEntity();
            deliveryServiceEntity2.setOutletId(outletEntity.getOutletId());
            deliveryServiceEntity2.setGroupCode(CoreConstants.DELIVERY_PAY_N_GET);
            deliveryServiceEntity2.setCode(CoreConstants.DELIVERY_PAY_N_GET_CODE);
            deliveryServiceEntity2.setTitle(CoreConstants.DELIVERY_PAY_N_GET_TITLE);
            deliveryServiceEntity2.setPrice(10D);
            deliveryServiceEntity2.setStatus(true);
            deliveryServiceEntity2.setOrderBy(1);
            deliveryServiceEntity2.setDistance(1D);
            newDeliveryServices.add(deliveryServiceRepository.save(deliveryServiceEntity2));
        }

        // express delivery (SHIP_TO_HOME_EXPRESS)
        if (!deliveryServiceCodes.contains(CoreConstants.DELIVERY_PAY_N_GET_EXPRESS_CODE)) {
            DeliveryServiceEntity deliveryServiceEntity3 = new DeliveryServiceEntity();
            deliveryServiceEntity3.setOutletId(outletEntity.getOutletId());
            deliveryServiceEntity3.setGroupCode(CoreConstants.DELIVERY_PAY_N_GET);
            deliveryServiceEntity3.setCode(CoreConstants.DELIVERY_PAY_N_GET_EXPRESS_CODE);
            deliveryServiceEntity3.setTitle(CoreConstants.DELIVERY_PAY_N_GET_EXPRESS_TITLE);
            deliveryServiceEntity3.setPrice(15D);
            deliveryServiceEntity3.setStatus(true);
            deliveryServiceEntity3.setOrderBy(2);
            deliveryServiceEntity3.setDistance(1D);
            newDeliveryServices.add(deliveryServiceRepository.save(deliveryServiceEntity3));

        }
        return newDeliveryServices;
    }


}
