package com.robertosouza.dslearnbds.observers;

import com.robertosouza.dslearnbds.entities.Deliver;

public interface DeliverRevisionObserver {

	void onSaveRevision(Deliver deliver);
}
