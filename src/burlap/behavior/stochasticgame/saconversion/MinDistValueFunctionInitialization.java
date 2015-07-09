package burlap.behavior.stochasticgame.saconversion;

import java.util.List;

import burlap.behavior.singleagent.ValueFunctionInitialization;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.stochasticgames.gridgame.GridGame;
import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectInstance;
import burlap.oomdp.core.State;

public class MinDistValueFunctionInitialization implements
ValueFunctionInitialization {

	Domain ggDomain; 
	String agentName;
	public MinDistValueFunctionInitialization(Domain ggDomain, String agentName){
		this.ggDomain = ggDomain;
		this.agentName = agentName;

	}

	@Override
	public double value(State s) {

		String an = GridWorldDomain.CLASSAGENT;

		int agentNum = 0;
		if(agentName.contains("1")){
			agentNum = 1;
			System.out.println("____________________________________AgentNum: "+agentNum);
		}

		ObjectInstance agent = null;
		List<ObjectInstance> agentList  = s.getObjectsOfClass(an);
		for (ObjectInstance o : agentList){
			if(o.getStringValForAttribute(GridGame.ATTPN).contains(Integer.toString(agentNum))){
				agent = o;
			}
		}

		if(agent != null){
			//get agent position
			int ax = agent.getIntValForAttribute(GridWorldDomain.ATTX);

			int ay = agent.getIntValForAttribute(GridWorldDomain.ATTY);

			int lx;
			int ly;
			double closestMDist=Double.MAX_VALUE;
			double mdist;
			List<ObjectInstance> objects = s.getObjectsOfClass(GridGame.CLASSGOAL);
			System.out.println("Num goals" +objects.size());
			for(ObjectInstance oi : objects){


				//System.out.println("AgentNum: "+agentNum+" GT: "+oi.getIntValForAttribute("gt"));
				if(oi.getIntValForAttribute("gt")==agentNum+1){


					lx = oi.getIntValForAttribute(GridWorldDomain.ATTX);
					ly = oi.getIntValForAttribute(GridWorldDomain.ATTY);

					mdist = Math.abs(ax-lx) + Math.abs(ay-ly);
					System.out.println("Agent "+agentNum+"'s goal: "+lx+", "+ly+" agent at: "+ax+", "+ay+" mdist: "+mdist);
					if(mdist<closestMDist){
						closestMDist = mdist;
					}
				}



			}

			return -1.0*closestMDist;
		}else{
			return Double.NEGATIVE_INFINITY;
		}
	}

	@Override
	public double qValue(State s, AbstractGroundedAction a) {
		value(s);
		return value(s);
	}

}
