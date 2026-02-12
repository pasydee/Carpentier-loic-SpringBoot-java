package SafetyNet.Alerts.controller;

import SafetyNet.Alerts.dto.FloodStationsResponse;
import SafetyNet.Alerts.service.FloodStationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class FloodStationsController {

    private final FloodStationsService service;

    public FloodStationsController(FloodStationsService service) {
        this.service = service;
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<FloodStationsResponse> getFloodInfo(@RequestParam("stations") String stations) throws Exception {

        log.info("GET /flood/stations - Request received for stations={}", stations);
        log.debug("Raw stations parameter: {}", stations);

        List<Integer> stationNumbers = Arrays.stream(stations.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        log.debug("Parsed station numbers: {}", stationNumbers);

        FloodStationsResponse response = service.getFloodInfo(stationNumbers);

        log.info("FloodStations response successfully generated for stations={}", stations);
        log.debug("Response content: {}", response);

        return ResponseEntity.ok(response);
    }
}
