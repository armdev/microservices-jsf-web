/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.mongo.signer;

import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
public class Device {

    private boolean normal;
    private boolean tablet;
    private boolean mobile;

}
