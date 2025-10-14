package com.helpflow.application.dtos;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DesempenhoEquipaDTO {
    private List<RankingAtendenteDTO> rankingAtendentes;
    private Map<String, Long> volumePorUsuario;
    private Double satisfacaoGeral;
}
