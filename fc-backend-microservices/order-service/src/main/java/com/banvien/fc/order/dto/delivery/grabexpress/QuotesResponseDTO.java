package com.banvien.fc.order.dto.delivery.grabexpress;

import java.util.List;

public class QuotesResponseDTO {
    private List<QuotesDTO> quotes;

    public List<QuotesDTO> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuotesDTO> quotes) {
        this.quotes = quotes;
    }
}
