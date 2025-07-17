package org.dmsextension.paperless.client.templates;

/**
 * Interface to represent and cluster paperless-ngx API DTOs
 */
public interface IDto {
    /**
     * Converts DTO to a JSON string that can, in turn, be pushed to the api.
     * @return Json string representation of the dto
     */
    String toJsonString();
}
