package com.travelbooking.controller;

import com.travelbooking.model.Train;
import com.travelbooking.service.TrainService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class TrainServlet extends HttpServlet {

    private final TrainService trainService = new TrainService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null && !id.isEmpty()) {
            // Single train detail
            Train train = trainService.findById(id);
            if (train == null) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("train", train);
            req.getRequestDispatcher("/WEB-INF/views/train-detail.jsp").forward(req, resp);
        } else {
            // Train listing
            String origin = req.getParameter("origin");
            String destination = req.getParameter("destination");
            List<Train> trains;
            if (origin != null && !origin.isEmpty()) {
                trains = trainService.searchByOrigin(origin);
                req.setAttribute("searchOrigin", origin);
            } else if (destination != null && !destination.isEmpty()) {
                trains = trainService.searchByDestination(destination);
                req.setAttribute("searchDestination", destination);
            } else {
                trains = trainService.findAll();
            }
            req.setAttribute("trains", trains);
            req.getRequestDispatcher("/WEB-INF/views/trains.jsp").forward(req, resp);
        }
    }
}
