/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.test_finale;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 *
 * @author yasmi
 */

public class MusicPlayerPanel extends JPanel {
    private List<File> musicFiles;
    private int currentTrackIndex = 0;
    private boolean isPlaying = false;
    private boolean isRandom = false;
    private Clip currentClip;
    private Timer progressTimer;
    
    // UI Components
    private JLabel trackLabel;
    private JProgressBar progressBar;
    private JSlider volumeSlider;
    private JButton playButton;
    private JButton prevButton;
    private JButton nextButton;
    private JToggleButton randomButton;
    
    public MusicPlayerPanel() {
        setLayout(new BorderLayout());
        
        // Load music files from src/musique directory
        loadMusicFiles();
        
        // Initialize UI components
        initComponents();
        
        // Set up timer for progress bar updates
        progressTimer = new Timer(100, e -> updateProgressBar());
    }
    
    private void loadMusicFiles() {
        musicFiles = new ArrayList<>();
        File musicDir = new File("src/musique"); // Vérifiez ce chemin
        System.out.println("Searching for music in: " + musicDir.getAbsolutePath()); // Debugging

        if (musicDir.exists() && musicDir.isDirectory()) {
            File[] files = musicDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav")); // Changez en .wav
            if (files != null) {
                for (File file : files) {
                    System.out.println("Found file: " + file.getName());
                }
                Collections.addAll(musicFiles, files);
            }
        }

        if (musicFiles.isEmpty()) {
            System.err.println("No music files found in: " + musicDir.getAbsolutePath());
        }
    }
    
    private void initComponents() {
        // Top panel for track info
        JPanel topPanel = new JPanel();
        trackLabel = new JLabel("No track selected");
        topPanel.add(trackLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for progress bar and file list
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(400, 20)); // Réduire la taille
        centerPanel.add(progressBar, BorderLayout.SOUTH);

        // File list
        JList<String> fileList = new JList<>(musicFiles.stream().map(File::getName).toArray(String[]::new));
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = fileList.getSelectedIndex();
                if (selectedIndex != -1) {
                    playTrack(selectedIndex);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(fileList);
        scrollPane.setPreferredSize(new Dimension(200, 150)); // Taille de la liste
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for controls
        JPanel bottomPanel = new JPanel(new FlowLayout());

        // Volume control
        volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.addChangeListener(e -> adjustVolume());
        bottomPanel.add(new JLabel("Volume:"));
        bottomPanel.add(volumeSlider);

        // Playback controls
        prevButton = new JButton("<<");
        prevButton.addActionListener(e -> playPrevious());
        bottomPanel.add(prevButton);

        playButton = new JButton("Play");
        playButton.addActionListener(e -> togglePlay());
        bottomPanel.add(playButton);

        nextButton = new JButton(">>");
        nextButton.addActionListener(e -> playNext());
        bottomPanel.add(nextButton);

        // Random mode toggle
        randomButton = new JToggleButton("Random");
        randomButton.addActionListener(e -> {
            isRandom = randomButton.isSelected();
            if (isRandom) {
                playRandomTrack();
            }
        });
        bottomPanel.add(randomButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void playTrack(int index) {
        if (musicFiles.isEmpty() || index < 0 || index >= musicFiles.size()) {
            return;
        }

        stopCurrentTrack();
        currentTrackIndex = index;

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFiles.get(index));
            currentClip = AudioSystem.getClip();
            currentClip.open(audioStream);

            // Set volume
            adjustVolume();

            // Update UI
            trackLabel.setText(musicFiles.get(index).getName());
            progressBar.setValue(0);

            // Start playback
            currentClip.start();
            isPlaying = true;
            playButton.setText("Pause");
            progressTimer.start();

            // Add listener for when track ends
            currentClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && isPlaying) { // Vérifiez si la lecture est active
                    playNext();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error playing track: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void stopCurrentTrack() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
        progressTimer.stop();
        isPlaying = false;
        playButton.setText("Play");
    }
    
    private void togglePlay() {
        if (currentClip == null) {
            playTrack(0);
        } else if (isPlaying) {
            currentClip.stop();
            isPlaying = false;
            playButton.setText("Play");
            progressTimer.stop();
        } else {
            currentClip.start();
            isPlaying = true;
            playButton.setText("Pause");
            progressTimer.start();
        }
    }
    
    private void playNext() {
        if (musicFiles.isEmpty()) return;
        
        int nextIndex;
        if (isRandom) {
            nextIndex = (int) (Math.random() * musicFiles.size());
        } else {
            nextIndex = (currentTrackIndex + 1) % musicFiles.size();
        }
        playTrack(nextIndex);
    }
    
    private void playPrevious() {
        if (musicFiles.isEmpty()) return;
        
        int prevIndex;
        if (isRandom) {
            prevIndex = (int) (Math.random() * musicFiles.size());
        } else {
            prevIndex = (currentTrackIndex - 1 + musicFiles.size()) % musicFiles.size();
        }
        playTrack(prevIndex);
    }
    
    private void adjustVolume() {
        if (currentClip != null && currentClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
            float volume = volumeSlider.getValue() / 100f;
            float dB = (float) (Math.log(volume == 0.0 ? 0.0001 : volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
    
    private void updateProgressBar() {
        if (currentClip != null && currentClip.isRunning()) {
            long position = currentClip.getMicrosecondPosition();
            long duration = currentClip.getMicrosecondLength();
            int progress = (int) (100 * position / duration);
            progressBar.setValue(progress);
            progressBar.setString(String.format("%d:%02d / %d:%02d", 
                position / 60000000, (position / 1000000) % 60,
                duration / 60000000, (duration / 1000000) % 60));
        }
    }
    
    private void playRandomTrack() {
        if (musicFiles.isEmpty()) return;
        int randomIndex = (int) (Math.random() * musicFiles.size());
        playTrack(randomIndex);
    }
}
