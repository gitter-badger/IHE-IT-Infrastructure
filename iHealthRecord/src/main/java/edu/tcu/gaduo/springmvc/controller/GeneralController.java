package edu.tcu.gaduo.springmvc.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class GeneralController<T> {

	public abstract T read(@PathVariable("_id") String _id);

	public abstract T vread(@PathVariable("_id") String _id, @PathVariable("_vid") String _vid, @RequestParam("_format") String _format);

	public abstract T update(@PathVariable("_id") String _id, T object, @RequestParam("_format") String _format);

	public abstract T delete(@PathVariable("_id") String _id, @RequestParam("_format") String _format);

	public abstract T create(T object, @RequestParam("_format") String _format);

	public abstract T validate(@PathVariable("_id") String _id);


}
