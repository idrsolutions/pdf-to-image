/*
 * Copyright (C) 2026 IDRsolutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.idrsolutions.examples;

import com.idrsolutions.image.JDeli;
import com.idrsolutions.image.encoder.options.EncoderOptions;
import com.idrsolutions.image.png.options.PngEncoderOptions;
import org.jpedal.examples.images.ConvertPagesToImages;

import java.awt.image.BufferedImage;
import java.io.File;

public class PdfToImage {

    public static void main(final String[] args) {

        // The ConvertPagesToImages class is used to render pages from a PDF as
        // BufferedImages which can then be written out in many different formats.

        // Initialize a new instance with a file
        final ConvertPagesToImages convertToImage = new ConvertPagesToImages("inputFile.pdf");

        // Control the page scaling
        // 1.33 gives the same output size as Acrobat at 100%
        convertToImage.setPageScaling(1.33f);

        try {

            // Attempt to decode the file
            // This method returns true if the decode was successful and false otherwise
            if (convertToImage.openPDFFile()) {

                // Get the number of pages in the document
                final int pageCount = convertToImage.getPageCount();

                // PDF page indices start from one, not zero!
                for (int page = 1; page <= pageCount; page++) {

                    // Convert the current page to a BufferedImage
                    final BufferedImage image = convertToImage.getPageAsImage(page);

                    // Use one of the many different EncoderOptions classes to
                    // select the image format as well as other options
                    // Supported formats are: AVIF, BMP, GIF, HEIC, JPEG, JPEG2000, PNG, TIFF, WEBP
                    final EncoderOptions options = new PngEncoderOptions();

                    // Write out the image using the JDeli encoder
                    JDeli.write(image, options, new File("output.png"));
                }
            }

        } catch (Exception e) {
            // An Exception may be thrown if the conversion fails
            e.printStackTrace(System.err);
        } finally {
            // You should always call this to free up system resources
            convertToImage.closePDFfile();
        }

    }

}
