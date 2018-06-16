package ru.gdgkazan.githubmvp.screen.walkthrough;

import java.util.List;

import ru.gdgkazan.githubmvp.content.Benefit;


public interface WalkthroughView  {
    void openAuthActivityScreen();
    void showBenefit(int index, boolean isLastBenefit);
    void setBenefits(List<Benefit> benefits);
}
