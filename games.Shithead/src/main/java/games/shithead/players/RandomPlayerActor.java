package games.shithead.players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import games.shithead.game.ActionValidator;
import games.shithead.game.IGameCard;
import games.shithead.messages.PlayerActionInfo;

public class RandomPlayerActor extends PlayerActor {

	@Override
	public String getName() {
		return "Random Player";
	}

	@Override
	protected List<Integer> chooseRevealedTableCards(List<IGameCard> cards, int numOfRevealedTableCardsToChoose) {
		int remainingNumberOfCardsToChoose = numOfRevealedTableCardsToChoose;
		List<Integer> chosenRevealedTableCardIds = new ArrayList<Integer>();
		for(IGameCard card : cards) {
			if(remainingNumberOfCardsToChoose > 0) {
				this.revealedTableCards.add(card);
				chosenRevealedTableCardIds.add(card.getUniqueId());
				remainingNumberOfCardsToChoose--;
			}
			else {
				this.handCards.add(card);
			}
		}
		return chosenRevealedTableCardIds;
	}

	@Override
	protected PlayerActionInfo getPlayerMove() {
		List<Integer> cardsToPut = new LinkedList<>();
		int cardId;
		if(handCards.isEmpty()) {
			cardId = getFirstPlayableCardId(revealedTableCards);
			cardsToPut.add(cardId);
		}
		else {
			cardId = getFirstPlayableCardId(handCards);
			cardsToPut.add(cardId);
		}
		return new PlayerActionInfo(cardsToPut, nextMoveId);
	}

	private int getFirstPlayableCardId(List<IGameCard> cards) {
		for(IGameCard card : cards) {
			List<IGameCard> cardsToPlay = new LinkedList<>();
			cardsToPlay.add(card);
			if(ActionValidator.canPlay(cardsToPlay, pile)) {
				return card.getUniqueId();
			}
		}
		return -1;
	}

	@Override
	protected List<IGameCard> getInterruptionCards() {
		// Nah
		return null;
	}

}
