package com.helpflow.application.dtos;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DashboardGestorDTO {
    private VisaoGeralDTO visaoGeral;
    private MetricasSLADTO metricasSLA;
    private DesempenhoEquipaDTO desempenhoEquipa;
    private AnalisesTemporaisDTO analisesTemporais;
}


