package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.OrderedItem;
import com.spring.entity.RoomService;
import com.spring.exception.RoomServiceAlreadyExists;
import com.spring.exception.RoomServiceNotFound;
import com.spring.repository.OrderedItemRepository;
import com.spring.repository.RoomServiceRepository;

@Service
public class RoomServices {
	@Autowired
	private RoomServiceRepository repository;

	@Autowired
	private OrderedItemRepository orderedItemRepository;

	// add room service
	public RoomService addRoomService(RoomService roomService) throws RoomServiceAlreadyExists {
		// check whether roomexists or not before creating service , not neccessary
		RoomService existingRoomService = repository.findByRoomNumberAndCompleted(roomService.getRoomNumber(), false);
		if (existingRoomService == null || (existingRoomService != null && existingRoomService.isCompleted() == true)) {
			for (OrderedItem orderedItem : roomService.getOrderedItems()) {
				orderedItem.setRoomService(roomService);
			}
			return repository.save(roomService);
		} else {
			throw new RoomServiceAlreadyExists("RoomService Already Exists , Please Update Existing.");
		}

	}

	// update room service
	public RoomService updateRoomService(RoomService roomService, int serviceId) throws RoomServiceNotFound {

		RoomService oldService = repository.findById(serviceId).orElse(null);

		if (oldService != null && !oldService.isCompleted()) {
			oldService.setRoomNumber(roomService.getRoomNumber());
			oldService.setGuestName(roomService.getGuestName());
			oldService.setCompleted(roomService.isCompleted());

			repository.save(oldService);

			for (OrderedItem orderedItem : roomService.getOrderedItems()) {
				orderedItem.setRoomService(oldService);
			}
			oldService.getOrderedItems().addAll(roomService.getOrderedItems());

			return repository.save(oldService);
		}

		else

		{
			throw new RoomServiceNotFound("Service Id not found or Service already completed");
		}

	}

	// delete room service
	public RoomService deleteRoomService(int serviceId) throws RoomServiceNotFound {
		RoomService oldService = repository.findById(serviceId).orElse(null);

		if (oldService != null && !oldService.isCompleted()) {
			repository.deleteById(serviceId);
			return oldService;

		} else {
			throw new RoomServiceNotFound("Service Id not found or Service already deleted");
		}
	}

	// get room service by room number where roomservice isCompleted is false;
	public RoomService getRoomServiceByRoomNumberAndIsCompleted(String roomNumber, boolean completed)
			throws RoomServiceNotFound {
		RoomService roomService = repository.findByRoomNumberAndCompleted(roomNumber, completed);

		if (roomService != null) {
			return roomService;
		} else {
			throw new RoomServiceNotFound("Room Service not found for room number " + roomNumber);
		}
	}

	// update roomservice by room number and set completed to true--after the bill
	// is generated
	public boolean setServiceCompleted(String roomNumber, boolean completed) throws RoomServiceNotFound {
		RoomService roomService = repository.findByRoomNumberAndCompleted(roomNumber, false);
		if (roomService != null) {
			roomService.setCompleted(completed);
			repository.save(roomService);
			return true;
		} else {
			throw new RoomServiceNotFound("Room Service not found for room number " + roomNumber);
		}
	}

	// get ActiveRoomServices
	public List<RoomService> getActiveRoomServices() {
		return repository.findByCompleted(false);
	}

	// delete orderedItem by id
	public void deleteOrderedItemById(int id) {
		orderedItemRepository.deleteById(id);

	}

}
