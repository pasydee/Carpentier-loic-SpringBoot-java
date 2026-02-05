package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FloodStationsResponse;
import SafetyNet.Alerts.service.FloodStationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FloodStationsController {

    private final FloodStationsService service;

    public FloodStationsController(FloodStationsService service) {
        this.service = service;
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<FloodStationsResponse> getFloodInfo(@RequestParam("stations") String stations) throws Exception {

        List<Integer> stationNumbers = Arrays.stream(stations.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return ResponseEntity.ok(service.getFloodInfo(stationNumbers));
    }
}
