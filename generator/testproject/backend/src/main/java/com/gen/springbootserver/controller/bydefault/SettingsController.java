package com.gen.springbootserver.controller.bydefault;

import javax.validation.Valid;

import com.gen.springbootserver.dto.bydefault.SettingsDTO;
import com.gen.springbootserver.service.bydefault.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for managing user settings
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    SettingsService settingsService;

      /**
     * Get current user settings
     *
     *
     * @return current settings data
     */
    @GetMapping("/current")
    public ResponseEntity<SettingsDTO> getCurrentUserSettings() {
        return ok(settingsService.getCurrentSettings());
    }

     /**
     * Update current user settings
     *
     * @param settingsDTO updated settings data
     * @return updated settings data
     */
    @PutMapping("/current")
    public ResponseEntity<SettingsDTO> updateCurrentUserSettings(@Valid @RequestBody SettingsDTO settingsDTO) {
        SettingsDTO updatedSettings = settingsService.updateCurrentSettings(settingsDTO);
        return ok(updatedSettings);
    }

    
}