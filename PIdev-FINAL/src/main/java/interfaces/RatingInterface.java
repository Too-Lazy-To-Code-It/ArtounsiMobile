/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import models.Rate;

/**
 *
 * @author achref
 */
public interface RatingInterface {
    
    public void updateRating(Rate rate);
    public Double fetchRating(Rate rate);
    public Double fetchRatingAVG(Rate rate);
    
}
