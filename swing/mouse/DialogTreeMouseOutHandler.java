/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.a2g.core.swing.mouse;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class DialogTreeMouseOutHandler extends MouseAdapter {
    private final java.awt.Label label;

    public DialogTreeMouseOutHandler(java.awt.Label label) {
        this.label = label;
    }	

    @Override
    public void mouseExited(MouseEvent event) {
        this.label.setBackground(new Color(123));
    }
}
