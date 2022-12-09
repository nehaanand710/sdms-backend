package com.ase.project.sdms.service.dashboard;

import com.ase.project.sdms.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    String dashboardIndex();

    TestResponseDto testResponse();

    String simulateSensors(SimulationDTO simulation);

    DashboardDto dashboardData(UsernameDto usernameDto);

    String updateEventHistoryActionFlag(EventHistoryUpdateDto actionValue);
}
