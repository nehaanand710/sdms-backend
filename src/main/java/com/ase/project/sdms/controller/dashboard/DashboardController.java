package com.ase.project.sdms.controller.dashboard;

import com.ase.project.sdms.dto.*;
import com.ase.project.sdms.service.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboardIndex() {
        return dashboardService.dashboardIndex();
    }

    @GetMapping("/testResponse")
    public TestResponseDto testResponse() {
        return dashboardService.testResponse();
    }

    @PostMapping("/simulation")
    public String simulateSensors(@RequestBody SimulationDTO simulation) {
        return dashboardService.simulateSensors(simulation);

    }

    @PostMapping("/getDashboardData")
    public DashboardDto dashboardData(@Valid @RequestBody UsernameDto usernameDto) {
        return dashboardService.dashboardData(usernameDto);
    }

    @PostMapping("/updateEventHistoryActionFlag")
    public String updateEventHistoryActionFlag( @RequestBody EventHistoryUpdateDto eventHistoryUpdateDto) {
        return  dashboardService.updateEventHistoryActionFlag(eventHistoryUpdateDto);
    }

}
